// ==========================================
// UniAssist Frontend JavaScript
// ==========================================

const API_BASE = "/api";


// ==========================================
// PAGE NAVIGATION
// ==========================================

function showSection(sectionId, clickedButton) {

    // Hide all sections
    const sections = document.querySelectorAll(".section");

    sections.forEach(section => {
        section.classList.remove("active");
    });

    // Show selected section
    const selectedSection = document.getElementById(sectionId);

    if (selectedSection) {
        selectedSection.classList.add("active");
    }

    // Update navigation buttons
    const navItems = document.querySelectorAll(".nav-item");

    navItems.forEach(item => {
        item.classList.remove("active");
    });

    if (clickedButton) {
        clickedButton.classList.add("active");
    }

    // Update page title
    const titles = {
        dashboard: "Dashboard",
        assistant: "AI Assistant",
        faqs: "Frequently Asked Questions",
        tickets: "Support Tickets"
    };

    const pageTitle = document.getElementById("page-title");

    if (pageTitle) {
        pageTitle.textContent = titles[sectionId] || "UniAssist";
    }

    // Load section data
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
// OPEN SECTION FROM QUICK SERVICE
// ==========================================

function showSectionByName(sectionId) {

    const navButton = document.querySelector(
        `.nav-item[onclick*="'${sectionId}'"]`
    );

    showSection(sectionId, navButton);
}


// ==========================================
// DASHBOARD
// ==========================================

async function loadDashboard() {

    try {

        const response = await fetch(
            `${API_BASE}/dashboard`
        );

        if (!response.ok) {
            throw new Error("Dashboard API failed");
        }

        const data = await response.json();

        document.getElementById("totalStudents").textContent =
            data.totalStudents ?? 0;

        document.getElementById("totalFAQs").textContent =
            data.totalFAQs ?? 0;

        document.getElementById("totalTickets").textContent =
            data.totalTickets ?? 0;

        document.getElementById("openTickets").textContent =
            data.openTickets ?? 0;

    } catch (error) {

        console.error(
            "Dashboard error:",
            error
        );
    }
}


// ==========================================
// CHAT
// ==========================================

async function sendMessage() {

    const input =
        document.getElementById("chatInput");

    const message =
        input.value.trim();

    if (!message) {
        return;
    }

    // Add user message
    addChatMessage(
        message,
        "user"
    );

    input.value = "";

    // Show typing message
    const typingId =
        addChatMessage(
            "Thinking...",
            "bot"
        );

    try {

        const response = await fetch(
            `${API_BASE}/chat`,
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    message: message
                })
            }
        );

        if (!response.ok) {
            throw new Error("Chat API failed");
        }

        const data =
            await response.json();

        // Remove typing message
        removeChatMessage(typingId);

        // Add AI response
        addChatMessage(
            data.answer ||
            "Sorry, I couldn't generate a response.",
            "bot"
        );

    } catch (error) {

        console.error(
            "Chat error:",
            error
        );

        removeChatMessage(typingId);

        addChatMessage(
            "Sorry, I am unable to connect to the support server right now.",
            "bot"
        );
    }
}


// ==========================================
// ADD CHAT MESSAGE
// ==========================================

function addChatMessage(message, sender) {

    const chatMessages =
        document.getElementById("chatMessages");

    const messageWrapper =
        document.createElement("div");

    const messageId =
        "msg-" + Date.now() + Math.random();

    messageWrapper.id = messageId;

    messageWrapper.className =
        `message ${sender}`;

    if (sender === "bot") {

        messageWrapper.innerHTML = `
            <div class="message-avatar">
                🤖
            </div>

            <div class="message-content">
                <p>${escapeHtml(message)}</p>
            </div>
        `;

    } else {

        messageWrapper.innerHTML = `
            <div class="message-content">
                <p>${escapeHtml(message)}</p>
            </div>
        `;
    }

    chatMessages.appendChild(
        messageWrapper
    );

    // Automatically scroll down
    chatMessages.scrollTop =
        chatMessages.scrollHeight;

    return messageId;
}


// ==========================================
// REMOVE CHAT MESSAGE
// ==========================================

function removeChatMessage(messageId) {

    const message =
        document.getElementById(messageId);

    if (message) {
        message.remove();
    }
}


// ==========================================
// CHAT SUGGESTIONS
// ==========================================

function askSuggestion(question) {

    const input =
        document.getElementById("chatInput");

    input.value = question;

    sendMessage();
}


// ==========================================
// ENTER KEY FOR CHAT
// ==========================================

function handleChatKey(event) {

    if (event.key === "Enter") {

        event.preventDefault();

        sendMessage();
    }
}


// ==========================================
// FAQ
// ==========================================

async function loadFAQs() {

    const faqList =
        document.getElementById("faqList");

    faqList.innerHTML =
        `<div class="loading">Loading FAQs...</div>`;

    try {

        const response =
            await fetch(`${API_BASE}/faqs`);

        if (!response.ok) {
            throw new Error("FAQ API failed");
        }

        const faqs =
            await response.json();

        displayFAQs(faqs);

    } catch (error) {

        console.error(
            "FAQ error:",
            error
        );

        faqList.innerHTML =
            `<div class="empty-state">
                Unable to load FAQs.
            </div>`;
    }
}


// ==========================================
// DISPLAY FAQS
// ==========================================

function displayFAQs(faqs) {

    const faqList =
        document.getElementById("faqList");

    if (!faqs || faqs.length === 0) {

        faqList.innerHTML =
            `<div class="empty-state">
                No FAQs found.
            </div>`;

        return;
    }

    faqList.innerHTML =
        faqs.map(faq => {

            return `
                <div class="faq-card">

                    <div class="faq-category">
                        ${escapeHtml(faq.category)}
                    </div>

                    <h3>
                        ${escapeHtml(faq.question)}
                    </h3>

                    <p>
                        ${escapeHtml(faq.answer)}
                    </p>

                </div>
            `;

        }).join("");
}


// ==========================================
// FAQ SEARCH
// ==========================================

async function searchFAQs() {

    const keyword =
        document.getElementById("faqSearch")
            .value
            .trim();

    if (!keyword) {

        loadFAQs();

        return;
    }

    const faqList =
        document.getElementById("faqList");

    faqList.innerHTML =
        `<div class="loading">Searching...</div>`;

    try {

        const response =
            await fetch(
                `${API_BASE}/faqs/search?keyword=${encodeURIComponent(keyword)}`
            );

        if (!response.ok) {
            throw new Error("FAQ search failed");
        }

        const faqs =
            await response.json();

        displayFAQs(faqs);

    } catch (error) {

        console.error(
            "FAQ search error:",
            error
        );

        faqList.innerHTML =
            `<div class="empty-state">
                Search failed.
            </div>`;
    }
}


// ==========================================
// CREATE SUPPORT TICKET
// ==========================================

async function createTicket(event) {

    event.preventDefault();

    const ticket = {

        studentName:
            document.getElementById(
                "ticketName"
            ).value.trim(),

        email:
            document.getElementById(
                "ticketEmail"
            ).value.trim(),

        category:
            document.getElementById(
                "ticketCategory"
            ).value,

        description:
            document.getElementById(
                "ticketDescription"
            ).value.trim()
    };

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
                        JSON.stringify(ticket)
                }
            );

        if (!response.ok) {
            throw new Error("Ticket creation failed");
        }

        const savedTicket =
            await response.json();

        alert(
            `Ticket #${savedTicket.id} created successfully!`
        );

        document
            .getElementById("ticketForm")
            .reset();

        loadTickets();

        // Refresh dashboard numbers
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
        document.getElementById("ticketList");

    ticketList.innerHTML =
        `<div class="loading">
            Loading tickets...
        </div>`;

    try {

        const response =
            await fetch(
                `${API_BASE}/tickets`
            );

        if (!response.ok) {
            throw new Error("Ticket API failed");
        }

        const tickets =
            await response.json();

        displayTickets(tickets);

    } catch (error) {

        console.error(
            "Ticket loading error:",
            error
        );

        ticketList.innerHTML =
            `<div class="empty-state">
                Unable to load tickets.
            </div>`;
    }
}


// ==========================================
// DISPLAY TICKETS
// ==========================================

function displayTickets(tickets) {

    const ticketList =
        document.getElementById("ticketList");

    if (!tickets || tickets.length === 0) {

        ticketList.innerHTML =
            `<div class="empty-state">
                No tickets created yet.
            </div>`;

        return;
    }

    ticketList.innerHTML =
        tickets.map(ticket => {

            return `
                <div class="ticket-item">

                    <div class="ticket-item-top">

                        <h3>
                            Ticket #${ticket.id}
                        </h3>

                        <span class="ticket-status">
                            ${escapeHtml(ticket.status)}
                        </span>

                    </div>

                    <p>
                        <strong>
                            ${escapeHtml(ticket.category)}
                        </strong>
                    </p>

                    <p>
                        ${escapeHtml(ticket.description)}
                    </p>

                </div>
            `;

        }).join("");
}


// ==========================================
// SECURITY HELPER
// ==========================================

function escapeHtml(value) {

    if (value === null ||
        value === undefined) {

        return "";
    }

    return String(value)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}


// ==========================================
// INITIALIZE APPLICATION
// ==========================================

document.addEventListener(
    "DOMContentLoaded",
    () => {

        console.log(
            "UniAssist frontend loaded."
        );

        loadDashboard();

    }
);