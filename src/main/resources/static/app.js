// ==========================================
// NodeFix Frontend JavaScript
// ==========================================

const API_BASE = "/api";


// ==========================================
// PAGE NAVIGATION
// ==========================================

function showSection(sectionId, clickedButton) {

    const sections =
        document.querySelectorAll(".section");

    sections.forEach(section => {
        section.classList.remove("active");
    });


    const selectedSection =
        document.getElementById(sectionId);

    if (selectedSection) {
        selectedSection.classList.add("active");
    }


    const navItems =
        document.querySelectorAll(".nav-item");

    navItems.forEach(item => {
        item.classList.remove("active");
    });


    if (clickedButton) {
        clickedButton.classList.add("active");
    }


    const titles = {

        dashboard:
            "Dashboard",

        assistant:
            "AI Assistant",

        faqs:
            "Frequently Asked Questions",

        tickets:
            "Support Tickets"
    };


    const pageTitle =
        document.getElementById("page-title");

    if (pageTitle) {

        pageTitle.textContent =
            titles[sectionId] ||
            "NodeFix";
    }


    if (sectionId === "dashboard") {
        loadDashboard();
    }


    if (sectionId === "faqs") {
        loadFAQs();
    }


    if (sectionId === "tickets") {
        loadTickets();
    }
}


// ==========================================
// OPEN SECTION
// ==========================================

function showSectionByName(sectionId) {

    const navButton =
        document.querySelector(
            `.nav-item[onclick*="'${sectionId}'"]`
        );

    showSection(
        sectionId,
        navButton
    );
}


// ==========================================
// DASHBOARD
// ==========================================

async function loadDashboard() {

    try {

        const response =
            await fetch(
                `${API_BASE}/dashboard`
            );


        if (!response.ok) {
            throw new Error(
                "Dashboard API failed"
            );
        }


        const data =
            await response.json();


        setText(
            "totalStudents",
            data.totalStudents ?? 0
        );


        setText(
            "totalFAQs",
            data.totalFAQs ?? 0
        );


        setText(
            "totalTickets",
            data.totalTickets ?? 0
        );


        setText(
            "openTickets",
            data.openTickets ?? 0
        );

    } catch (error) {

        console.error(
            "Dashboard error:",
            error
        );
    }
}


function setText(
    id,
    value
) {

    const element =
        document.getElementById(id);

    if (element) {
        element.textContent = value;
    }
}


// ==========================================
// CHAT
// ==========================================

async function sendMessage() {

    const input =
        document.getElementById(
            "chatInput"
        );


    if (!input) {
        return;
    }


    const message =
        input.value.trim();


    if (!message) {
        return;
    }


    // --------------------------------------
    // USER MESSAGE
    // --------------------------------------

    addChatMessage(
        message,
        "user"
    );


    input.value = "";


    // --------------------------------------
    // TYPING INDICATOR
    // --------------------------------------

    const typingId =
        addTypingMessage();


    setChatSendingState(
        true
    );


    // --------------------------------------
    // LANGUAGE
    // --------------------------------------

    const languageSelect =
        document.getElementById(
            "chatLanguage"
        );


    const language =
        languageSelect
            ? languageSelect.value
            : "English";


    // --------------------------------------
    // OPTIONAL STUDENT INFORMATION
    // --------------------------------------

    const studentName =
        getStoredStudentName();


    const email =
        getStoredStudentEmail();


    try {

        const response =
            await fetch(
                `${API_BASE}/chat`,
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body:
                        JSON.stringify({

                            message:
                                message,

                            language:
                                language,

                            studentName:
                                studentName,

                            email:
                                email
                        })
                }
            );


        let data = {};


        try {

            data =
                await response.json();

        } catch (_) {

            data = {};
        }


        // --------------------------------------
        // API ERROR
        // --------------------------------------

        if (!response.ok) {

            throw new Error(
                data.answer ||
                data.message ||
                `Chat API failed: ${response.status}`
            );
        }


        // --------------------------------------
        // REMOVE TYPING
        // --------------------------------------

        removeChatMessage(
            typingId
        );


        // --------------------------------------
        // AI ANSWER
        // --------------------------------------

        addChatMessage(
            data.answer ||
            "Sorry, I couldn't generate a response.",
            "bot",
            data.source ||
            "NodeFix"
        );


        // --------------------------------------
        // AUTOMATIC TICKET
        // --------------------------------------

        if (
            data.ticketCreated &&
            data.ticketId
        ) {

            addTicketNotice(
                data.ticketId,
                data.ticketCategory,
                data.ticketStatus
            );


            loadDashboard();

            loadTickets();
        }

    } catch (error) {

        console.error(
            "Chat error:",
            error
        );


        removeChatMessage(
            typingId
        );


        addChatMessage(
            "Sorry, I am unable to connect to the support server right now.",
            "bot",
            "NodeFix"
        );

    } finally {

        setChatSendingState(
            false
        );


        input.focus();
    }
}


// ==========================================
// CHAT MESSAGE
// ==========================================

function addChatMessage(
    message,
    sender,
    source
) {

    const chatMessages =
        document.getElementById(
            "chatMessages"
        );


    if (!chatMessages) {
        return null;
    }


    const messageWrapper =
        document.createElement(
            "div"
        );


    const messageId =
        `msg-${Date.now()}-${Math.random()
            .toString(36)
            .slice(2, 8)}`;


    messageWrapper.id =
        messageId;


    messageWrapper.className =
        `message ${sender}`;


    const safeMessage =
        formatMessage(
            message
        );


    if (sender === "bot") {

        const safeSource =
            source
                ? escapeHtml(source)
                : "";


        messageWrapper.innerHTML = `

            <div
                class="message-avatar"
                aria-hidden="true"
            >
                N
            </div>

            <div class="message-content">

                <p>
                    ${safeMessage}
                </p>

                ${
                    safeSource
                        ? `
                            <small
                                class="message-source"
                            >
                                ${safeSource}
                            </small>
                        `
                        : ""
                }

            </div>

        `;

    } else {

        messageWrapper.innerHTML = `

            <div class="message-content">

                <p>
                    ${safeMessage}
                </p>

            </div>

        `;
    }


    chatMessages.appendChild(
        messageWrapper
    );


    scrollChatToBottom();


    return messageId;
}


// ==========================================
// TYPING INDICATOR
// ==========================================

function addTypingMessage() {

    const chatMessages =
        document.getElementById(
            "chatMessages"
        );


    if (!chatMessages) {
        return null;
    }


    const messageWrapper =
        document.createElement(
            "div"
        );


    const messageId =
        `typing-${Date.now()}-${Math.random()
            .toString(36)
            .slice(2, 8)}`;


    messageWrapper.id =
        messageId;


    messageWrapper.className =
        "message bot typing-message";


    messageWrapper.innerHTML = `

        <div
            class="message-avatar"
            aria-hidden="true"
        >
            N
        </div>


        <div class="message-content">

            <p
                class="typing-bubble"
                aria-label="NodeFix is thinking"
            >

                <span></span>
                <span></span>
                <span></span>

            </p>

        </div>
    `;


    chatMessages.appendChild(
        messageWrapper
    );


    scrollChatToBottom();


    return messageId;
}


// ==========================================
// REMOVE MESSAGE
// ==========================================

function removeChatMessage(
    messageId
) {

    if (!messageId) {
        return;
    }


    const message =
        document.getElementById(
            messageId
        );


    if (message) {
        message.remove();
    }
}


// ==========================================
// AUTOMATIC TICKET NOTICE
// ==========================================

function addTicketNotice(
    ticketId,
    category,
    status
) {

    const categoryText =
        category
            ? `Category: ${category}`
            : "Category: Other";


    const statusText =
        status
            ? `Status: ${status}`
            : "Status: OPEN";


    addChatMessage(
        `A support ticket #${ticketId} has been created automatically.\n${categoryText}\n${statusText}`,
        "bot",
        "NodeFix Support"
    );
}


// ==========================================
// SUGGESTIONS
// ==========================================

function askSuggestion(
    question
) {

    const input =
        document.getElementById(
            "chatInput"
        );


    if (!input) {
        return;
    }


    input.value =
        question;


    input.focus();


    sendMessage();
}


// ==========================================
// ENTER KEY
// ==========================================

function handleChatKey(
    event
) {

    if (event.key === "Enter") {

        event.preventDefault();

        sendMessage();
    }
}


// ==========================================
// CHAT SENDING STATE
// ==========================================

function setChatSendingState(
    isSending
) {

    const input =
        document.getElementById(
            "chatInput"
        );


    const button =
        document.querySelector(
            ".chat-input-area > button"
        );


    if (input) {
        input.disabled =
            isSending;
    }


    if (button) {

        button.disabled =
            isSending;


        button.style.opacity =
            isSending
                ? "0.65"
                : "1";


        button.style.cursor =
            isSending
                ? "wait"
                : "pointer";
    }
}


// ==========================================
// SCROLL CHAT
// ==========================================

function scrollChatToBottom() {

    const chatMessages =
        document.getElementById(
            "chatMessages"
        );


    if (!chatMessages) {
        return;
    }


    requestAnimationFrame(
        () => {

            chatMessages.scrollTop =
                chatMessages.scrollHeight;

        }
    );
}


// ==========================================
// FAQ
// ==========================================

async function loadFAQs() {

    const faqList =
        document.getElementById(
            "faqList"
        );


    if (!faqList) {
        return;
    }


    faqList.innerHTML =
        `
            <div class="loading">
                Loading FAQs...
            </div>
        `;


    try {

        const response =
            await fetch(
                `${API_BASE}/faqs`
            );


        if (!response.ok) {

            throw new Error(
                "FAQ API failed"
            );
        }


        const faqs =
            await response.json();


        displayFAQs(
            faqs
        );

    } catch (error) {

        console.error(
            "FAQ error:",
            error
        );


        faqList.innerHTML =
            `
                <div class="empty-state">
                    Unable to load FAQs.
                </div>
            `;
    }
}


// ==========================================
// DISPLAY FAQS
// ==========================================

function displayFAQs(
    faqs
) {

    const faqList =
        document.getElementById(
            "faqList"
        );


    if (!faqList) {
        return;
    }


    if (
        !Array.isArray(faqs) ||
        faqs.length === 0
    ) {

        faqList.innerHTML =
            `
                <div class="empty-state">
                    No FAQs found.
                </div>
            `;

        return;
    }


    faqList.innerHTML =
        faqs
            .map(
                faq => `

                    <div class="faq-card">

                        <div class="faq-category">
                            ${escapeHtml(
                                faq.category
                            )}
                        </div>

                        <h3>
                            ${escapeHtml(
                                faq.question
                            )}
                        </h3>

                        <p>
                            ${escapeHtml(
                                faq.answer
                            )}
                        </p>

                    </div>
                `
            )
            .join("");
}


// ==========================================
// FAQ SEARCH
// ==========================================

async function searchFAQs() {

    const searchInput =
        document.getElementById(
            "faqSearch"
        );


    const faqList =
        document.getElementById(
            "faqList"
        );


    if (
        !searchInput ||
        !faqList
    ) {
        return;
    }


    const keyword =
        searchInput.value.trim();


    if (!keyword) {

        loadFAQs();

        return;
    }


    faqList.innerHTML =
        `
            <div class="loading">
                Searching...
            </div>
        `;


    try {

        const response =
            await fetch(
                `${API_BASE}/faqs/search?keyword=${encodeURIComponent(keyword)}`
            );


        if (!response.ok) {

            throw new Error(
                "FAQ search failed"
            );
        }


        const faqs =
            await response.json();


        displayFAQs(
            faqs
        );

    } catch (error) {

        console.error(
            "FAQ search error:",
            error
        );


        faqList.innerHTML =
            `
                <div class="empty-state">
                    Search failed.
                </div>
            `;
    }
}


// ==========================================
// MANUAL TICKET
// ==========================================

async function createTicket(
    event
) {

    event.preventDefault();


    const nameElement =
        document.getElementById(
            "ticketName"
        );


    const emailElement =
        document.getElementById(
            "ticketEmail"
        );


    const categoryElement =
        document.getElementById(
            "ticketCategory"
        );


    const descriptionElement =
        document.getElementById(
            "ticketDescription"
        );


    if (
        !nameElement ||
        !emailElement ||
        !categoryElement ||
        !descriptionElement
    ) {
        return;
    }


    const ticket = {

        studentName:
            nameElement.value.trim(),

        email:
            emailElement.value.trim(),

        category:
            categoryElement.value,

        description:
            descriptionElement.value.trim()
    };


    if (
        !ticket.studentName ||
        !ticket.email ||
        !ticket.category ||
        !ticket.description
    ) {

        alert(
            "Please complete all required ticket fields."
        );

        return;
    }


    try {

        const response =
            await fetch(
                `${API_BASE}/tickets`,
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body:
                        JSON.stringify(
                            ticket
                        )
                }
            );


        if (!response.ok) {

            throw new Error(
                "Ticket creation failed"
            );
        }


        const savedTicket =
            await response.json();


        alert(
            `Ticket #${savedTicket.id} created successfully.`
        );


        const form =
            document.getElementById(
                "ticketForm"
            );


        if (form) {
            form.reset();
        }


        saveStudentDetails(
            ticket.studentName,
            ticket.email
        );


        loadTickets();

        loadDashboard();

    } catch (error) {

        console.error(
            "Ticket error:",
            error
        );


        alert(
            "Unable to create the ticket. Please try again."
        );
    }
}


// ==========================================
// LOAD TICKETS
// ==========================================

async function loadTickets() {

    const ticketList =
        document.getElementById(
            "ticketList"
        );


    if (!ticketList) {
        return;
    }


    ticketList.innerHTML =
        `
            <div class="loading">
                Loading tickets...
            </div>
        `;


    try {

        const response =
            await fetch(
                `${API_BASE}/tickets`
            );


        if (!response.ok) {

            throw new Error(
                "Ticket API failed"
            );
        }


        const tickets =
            await response.json();


        displayTickets(
            tickets
        );

    } catch (error) {

        console.error(
            "Ticket loading error:",
            error
        );


        ticketList.innerHTML =
            `
                <div class="empty-state">
                    Unable to load tickets.
                </div>
            `;
    }
}


// ==========================================
// DISPLAY TICKETS
// ==========================================

function displayTickets(
    tickets
) {

    const ticketList =
        document.getElementById(
            "ticketList"
        );


    if (!ticketList) {
        return;
    }


    if (
        !Array.isArray(tickets) ||
        tickets.length === 0
    ) {

        ticketList.innerHTML =
            `
                <div class="empty-state">
                    No tickets created yet.
                </div>
            `;

        return;
    }


    ticketList.innerHTML =
        tickets
            .map(
                ticket => `

                    <div class="ticket-item">

                        <div class="ticket-item-top">

                            <h3>
                                Ticket #${escapeHtml(
                                    ticket.id
                                )}
                            </h3>

                            <span class="ticket-status">
                                ${escapeHtml(
                                    ticket.status
                                )}
                            </span>

                        </div>


                        <p>
                            <strong>
                                ${escapeHtml(
                                    ticket.category
                                )}
                            </strong>
                        </p>


                        <p>
                            ${escapeHtml(
                                ticket.description
                            )}
                        </p>

                    </div>
                `
            )
            .join("");
}


// ==========================================
// STUDENT DETAILS
// ==========================================

function saveStudentDetails(
    name,
    email
) {

    try {

        if (name) {

            localStorage.setItem(
                "nodefixStudentName",
                name
            );
        }


        if (email) {

            localStorage.setItem(
                "nodefixStudentEmail",
                email
            );
        }

    } catch (error) {

        console.warn(
            "Could not save student details.",
            error
        );
    }
}


function getStoredStudentName() {

    try {

        return (
            localStorage.getItem(
                "nodefixStudentName"
            ) || ""
        );

    } catch (_) {

        return "";
    }
}


function getStoredStudentEmail() {

    try {

        return (
            localStorage.getItem(
                "nodefixStudentEmail"
            ) || ""
        );

    } catch (_) {

        return "";
    }
}


// ==========================================
// MESSAGE FORMAT
// ==========================================

function formatMessage(
    message
) {

    if (
        message === null ||
        message === undefined
    ) {
        return "";
    }


    let safe =
        escapeHtml(
            message
        );


    /*
     * Make official URLs clickable.
     */
    safe =
        safe.replace(
            /(https?:\/\/[^\s<]+)/g,
            '<a href="$1" target="_blank" rel="noopener noreferrer">$1</a>'
        );


    safe =
        safe.replace(
            /\n/g,
            "<br>"
        );


    return safe;
}


// ==========================================
// SECURITY HELPER
// ==========================================

function escapeHtml(
    value
) {

    if (
        value === null ||
        value === undefined
    ) {
        return "";
    }


    return String(value)

        .replace(
            /&/g,
            "&amp;"
        )

        .replace(
            /</g,
            "&lt;"
        )

        .replace(
            />/g,
            "&gt;"
        )

        .replace(
            /"/g,
            "&quot;"
        )

        .replace(
            /'/g,
            "&#039;"
        );
}


// ==========================================
// INITIALIZE
// ==========================================

document.addEventListener(
    "DOMContentLoaded",
    () => {

        console.log(
            "NodeFix frontend loaded."
        );


        loadDashboard();

    }
);