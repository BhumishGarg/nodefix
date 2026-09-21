package com.university.studentsupport.service;

import com.university.studentsupport.model.FAQ;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UniversityFAQData {

    public List<FAQ> getFAQs() {

        List<FAQ> faqs = new ArrayList<>();

        // =====================================================
        // 1-10 : ADMISSIONS & ENROLLMENT
        // =====================================================

        faqs.add(new FAQ(
                "Admissions & Enrollment",
                "What are the eligibility requirements for admission to an undergraduate programme?",
                "Eligibility depends on the programme and the university's current admission rules. Check the official admission notice for academic qualifications, required subjects, minimum marks and entrance-test requirements."
        ));

        faqs.add(new FAQ(
                "Admissions & Enrollment",
                "How can I apply for admission to the university?",
                "Apply through the university's official admission portal. Complete the application form, upload the required documents, pay any applicable application fee and submit before the deadline."
        ));

        faqs.add(new FAQ(
                "Admissions & Enrollment",
                "What documents are required during the admission process?",
                "Common requirements include academic certificates or marksheets, identity proof, photographs and programme-specific documents. Check the current admission instructions for the exact list."
        ));

        faqs.add(new FAQ(
                "Admissions & Enrollment",
                "How can I check the status of my admission application?",
                "Log in to the official admission portal and open the application-status section. If the status is not updated, contact the admission office with your application number."
        ));

        faqs.add(new FAQ(
                "Admissions & Enrollment",
                "What is the deadline for completing admission formalities?",
                "Admission deadlines are programme- and intake-specific. Check the current admission notice or academic calendar because deadlines can change."
        ));

        faqs.add(new FAQ(
                "Admissions & Enrollment",
                "Can I change my programme after admission?",
                "Programme changes may be permitted subject to university rules, available seats, eligibility and approval from the relevant academic authorities."
        ));

        faqs.add(new FAQ(
                "Admissions & Enrollment",
                "What is the procedure for transferring credits from another university?",
                "Credit transfer normally requires submission of transcripts and course details for evaluation against the university's credit-transfer policy. Final approval is given by the authorised academic department."
        ));

        faqs.add(new FAQ(
                "Admissions & Enrollment",
                "Can an admitted student defer admission to a later semester?",
                "Deferral may be possible for approved reasons and under the university's admission policy. Contact the admission office before the reporting or enrolment deadline."
        ));

        faqs.add(new FAQ(
                "Admissions & Enrollment",
                "How can I obtain my student identification card?",
                "After completing enrolment, the university normally issues a student ID card through the designated student-services or administration office."
        ));

        faqs.add(new FAQ(
                "Admissions & Enrollment",
                "What should I do if there is an error in my admission record?",
                "Contact the admission or student-records office and provide supporting documents so the record can be corrected."
        ));


        // =====================================================
        // 11-20 : ACADEMIC & COURSES
        // =====================================================

        faqs.add(new FAQ(
                "Academic & Courses",
                "How can I register for courses for the upcoming semester?",
                "Use the student portal's course-registration section during the registration window. Select eligible courses, verify prerequisites and submit the registration."
        ));

        faqs.add(new FAQ(
                "Academic & Courses",
                "What happens if I miss the course registration deadline?",
                "Contact the academic or registrar's office immediately. Late registration may require approval and may not be available after the permitted period."
        ));

        faqs.add(new FAQ(
                "Academic & Courses",
                "How can I add or drop a course?",
                "Use the add/drop facility during the official add/drop period or submit the prescribed request to your department."
        ));

        faqs.add(new FAQ(
                "Academic & Courses",
                "What is a prerequisite course and why is it important?",
                "A prerequisite is a course or requirement that must normally be completed before taking another course. It ensures that students have the required background knowledge."
        ));

        faqs.add(new FAQ(
                "Academic & Courses",
                "How can I change my elective subject?",
                "Elective changes are usually allowed only during the approved registration or change period and subject to seat availability."
        ));

        faqs.add(new FAQ(
                "Academic & Courses",
                "How can I find the syllabus for my course?",
                "Course syllabi are normally available through the learning-management system, department portal or course instructor."
        ));

        faqs.add(new FAQ(
                "Academic & Courses",
                "Where can I find the academic calendar?",
                "The academic calendar contains important dates such as registration, teaching periods, examinations and holidays. Check the official university portal."
        ));

        faqs.add(new FAQ(
                "Academic & Courses",
                "How can I contact the academic department about a course issue?",
                "Contact the course instructor first when appropriate, followed by the programme coordinator or academic department if the issue remains unresolved."
        ));

        faqs.add(new FAQ(
                "Academic & Courses",
                "Can I take an additional course beyond my normal semester load?",
                "Additional courses may be allowed subject to credit-load limits, prerequisites, timetable availability and academic approval."
        ));

        faqs.add(new FAQ(
                "Academic & Courses",
                "How can I apply for a change of programme or specialization?",
                "Programme or specialization changes usually require an application, eligibility check, seat availability and approval from the relevant academic authorities."
        ));


        // =====================================================
        // 21-35 : EXAMINATIONS & GRADES
        // =====================================================

        faqs.add(new FAQ(
                "Examinations & Grades",
                "Where can I find the semester examination timetable?",
                "The semester examination timetable is normally published through the examination portal or official university notice system."
        ));

        faqs.add(new FAQ(
                "Examinations & Grades",
                "What should I do if two examinations are scheduled at the same time?",
                "Report the clash to the examination office immediately using the prescribed process."
        ));

        faqs.add(new FAQ(
                "Examinations & Grades",
                "What are the rules for appearing in semester examinations?",
                "Students must follow the published examination rules, including eligibility, identification, permitted materials and reporting time."
        ));

        faqs.add(new FAQ(
                "Examinations & Grades",
                "What should I do if I miss an examination because of an emergency?",
                "Contact the examination office as soon as possible and submit any required evidence for the emergency."
        ));

        faqs.add(new FAQ(
                "Examinations & Grades",
                "How can I apply for a re-examination or supplementary examination?",
                "Re-examination or supplementary opportunities depend on programme regulations. Check the examination notice and submit the prescribed application."
        ));

        faqs.add(new FAQ(
                "Examinations & Grades",
                "How can I request revaluation of an examination answer sheet?",
                "Revaluation is normally requested through the examination portal or examination office within a specified period after results."
        ));

        faqs.add(new FAQ(
                "Examinations & Grades",
                "When are semester results usually published?",
                "Results are published according to the examination office's academic schedule. Check the official result portal and university notices."
        ));

        faqs.add(new FAQ(
                "Examinations & Grades",
                "How can I download my marksheet or grade report?",
                "Log in to the student or examination portal and open the results or marksheet section."
        ));

        faqs.add(new FAQ(
                "Examinations & Grades",
                "What is the difference between a grade point and a credit?",
                "A credit measures the academic workload or value of a course, while a grade point represents performance in that course."
        ));

        faqs.add(new FAQ(
                "Examinations & Grades",
                "How is semester GPA calculated?",
                "Semester GPA is generally a credit-weighted average of grade points for courses taken in that semester."
        ));

        faqs.add(new FAQ(
                "Examinations & Grades",
                "How is cumulative GPA calculated?",
                "Cumulative GPA is generally calculated using credit-weighted grade points across completed semesters."
        ));

        faqs.add(new FAQ(
                "Examinations & Grades",
                "What should I do if I believe my grade has been entered incorrectly?",
                "Submit the prescribed correction or grievance request to the examination office within the allowed period."
        ));

        faqs.add(new FAQ(
                "Examinations & Grades",
                "What is an academic backlog and how can it affect graduation?",
                "An academic backlog usually means an incomplete or failed course that must be cleared. Backlogs can affect progression or graduation."
        ));

        faqs.add(new FAQ(
                "Examinations & Grades",
                "What are the requirements for graduating from an undergraduate programme?",
                "Graduation normally requires completion of all required credits, programme courses, academic requirements and any internship or project requirements."
        ));


        // =====================================================
        // 36-45 : ATTENDANCE
        // =====================================================

        faqs.add(new FAQ(
                "Attendance",
                "Where can I check my current attendance percentage?",
                "Attendance is normally available in the student portal or learning-management system."
        ));

        faqs.add(new FAQ(
                "Attendance",
                "What is the minimum attendance requirement for a course?",
                "The minimum attendance requirement is determined by the university's academic regulations and may differ by programme or course."
        ));

        faqs.add(new FAQ(
                "Attendance",
                "What should I do if my attendance is recorded incorrectly?",
                "Compare the portal entry with attendance records and contact the course instructor or department promptly."
        ));

        faqs.add(new FAQ(
                "Attendance",
                "Can attendance shortages be condoned?",
                "Condonation of attendance shortage, if available, is governed by university regulations and usually requires an approved reason and supporting documents."
        ));

        faqs.add(new FAQ(
                "Attendance",
                "How should I submit documentation for an approved absence?",
                "Submit approved absence or medical documentation through the university's prescribed leave or attendance process."
        ));

        faqs.add(new FAQ(
                "Attendance",
                "Does attendance in laboratory or practical classes have separate requirements?",
                "Laboratory and practical courses may have separate attendance requirements because they involve scheduled practical sessions."
        ));

        faqs.add(new FAQ(
                "Attendance",
                "What happens if my attendance falls below the required percentage?",
                "Low attendance can result in warnings, restrictions on examination eligibility or other academic consequences depending on university policy."
        ));

        faqs.add(new FAQ(
                "Attendance",
                "Who should I contact regarding an attendance dispute?",
                "Start with the course instructor or attendance coordinator and escalate to the department office if the discrepancy is not resolved."
        ));

        faqs.add(new FAQ(
                "Attendance",
                "How frequently is attendance updated in the student portal?",
                "Attendance update frequency depends on the instructor and university system."
        ));

        faqs.add(new FAQ(
                "Attendance",
                "Can medical or other approved leave affect attendance eligibility?",
                "Approved medical or other leave may be considered under the university's attendance policy. Submit valid documentation."
        ));


        // =====================================================
        // 46-55 : FEES & FINANCE
        // =====================================================

        faqs.add(new FAQ(
                "Fees & Finance",
                "How can I view my semester fee details?",
                "Log in to the student or fee portal and open the fee or financial-account section to view current semester charges, due dates and outstanding amounts."
        ));

        faqs.add(new FAQ(
                "Fees & Finance",
                "What payment methods are available for university fees?",
                "Available payment methods depend on the university and may include online banking, cards, UPI or other approved methods. Use only the official payment portal."
        ));

        faqs.add(new FAQ(
                "Fees & Finance",
                "What should I do if an online fee payment fails?",
                "First check whether the amount was actually debited. If it was debited but the portal is not updated, keep the transaction reference and contact the finance or accounts office."
        ));

        faqs.add(new FAQ(
                "Fees & Finance",
                "How can I download a fee payment receipt?",
                "After a successful payment, use the fee portal's receipt or transaction-history option to download the official receipt."
        ));

        faqs.add(new FAQ(
                "Fees & Finance",
                "What is the deadline for paying semester fees?",
                "Fee deadlines are published in the fee notice or academic calendar. Always verify the current semester's deadline."
        ));

        faqs.add(new FAQ(
                "Fees & Finance",
                "Is there a late fee for delayed payment?",
                "A late-payment charge may apply when fees are paid after the permitted deadline. The amount is determined by the university's current fee policy."
        ));

        faqs.add(new FAQ(
                "Fees & Finance",
                "How can I apply for a fee refund?",
                "Refund eligibility depends on the university's refund policy, withdrawal date and type of fee. Submit the prescribed refund request."
        ));

        faqs.add(new FAQ(
                "Fees & Finance",
                "Does the university offer scholarships or financial assistance?",
                "Many universities provide scholarships, merit awards or financial assistance subject to eligibility and funding. Check the official scholarship notices."
        ));

        faqs.add(new FAQ(
                "Fees & Finance",
                "How can I apply for a university scholarship?",
                "Apply through the university's designated scholarship or financial-aid portal and submit the required documents before the deadline."
        ));

        faqs.add(new FAQ(
                "Fees & Finance",
                "Whom should I contact if my fee payment is not reflected in the portal?",
                "Do not make a duplicate payment immediately. Keep the bank or transaction reference and contact the accounts office or portal support team."
        ));


        // =====================================================
        // 56-65 : LIBRARY
        // =====================================================

        faqs.add(new FAQ(
                "Library",
                "How can I obtain access to the university library?",
                "Students generally receive library access after enrolment and activation of their student account. Follow the library registration instructions."
        ));

        faqs.add(new FAQ(
                "Library",
                "What are the library opening and closing hours?",
                "Library hours vary by campus and may change during examinations or holidays. Check the library's official notice or portal."
        ));

        faqs.add(new FAQ(
                "Library",
                "How many books can a student borrow at one time?",
                "The borrowing limit depends on student category and university library rules. Check your library account."
        ));

        faqs.add(new FAQ(
                "Library",
                "How long can I keep a borrowed book?",
                "Loan duration depends on the type of material and student category. The due date is normally displayed in the library account."
        ));

        faqs.add(new FAQ(
                "Library",
                "How can I renew a borrowed book?",
                "Use the library portal to renew eligible items before the due date. Renewal may be blocked if another user has reserved the item."
        ));

        faqs.add(new FAQ(
                "Library",
                "What happens if I return a library book late?",
                "Late returns may result in fines, borrowing restrictions or other penalties according to library policy."
        ));

        faqs.add(new FAQ(
                "Library",
                "How can I search for a book in the library catalogue?",
                "Search the library catalogue using the title, author, subject or keywords. The catalogue normally shows availability and location."
        ));

        faqs.add(new FAQ(
                "Library",
                "Can students access electronic journals and research databases?",
                "Many universities provide access to electronic journals and research databases through the library portal."
        ));

        faqs.add(new FAQ(
                "Library",
                "What should I do if a borrowed book is lost or damaged?",
                "Report a lost or damaged item to the library immediately. The library will explain the replacement or penalty procedure."
        ));

        faqs.add(new FAQ(
                "Library",
                "How can I contact the library help desk?",
                "Use the library portal, official email or help-desk counter to contact library staff."
        ));


        // =====================================================
        // 66-75 : IT & STUDENT PORTAL
        // =====================================================

        faqs.add(new FAQ(
                "IT & Student Portal",
                "How do I log in to the student portal for the first time?",
                "Use the official student-portal URL and follow the account-activation or password-setup instructions."
        ));

        faqs.add(new FAQ(
                "IT & Student Portal",
                "What should I do if I forget my student portal password?",
                "Use the portal's password-reset option. If self-service reset does not work, contact the university IT help desk."
        ));

        faqs.add(new FAQ(
                "IT & Student Portal",
                "How can I update my phone number or email address?",
                "Profile changes may be available through the student portal. Restricted fields may require the student-records office."
        ));

        faqs.add(new FAQ(
                "IT & Student Portal",
                "What should I do if my student portal account is locked?",
                "Use password recovery or wait for the stated lockout period. If the account remains locked, contact IT support."
        ));

        faqs.add(new FAQ(
                "IT & Student Portal",
                "How can I access university Wi-Fi?",
                "Connect to the university's official Wi-Fi network and sign in with your student credentials if authentication is required."
        ));

        faqs.add(new FAQ(
                "IT & Student Portal",
                "What should I do if campus Wi-Fi is not working?",
                "Restart the connection, verify your credentials and contact IT support if the issue continues."
        ));

        faqs.add(new FAQ(
                "IT & Student Portal",
                "How can I access my university email account?",
                "Use the official university email portal or mail application with your university credentials."
        ));

        faqs.add(new FAQ(
                "IT & Student Portal",
                "How can I report a problem with a university computer or IT service?",
                "Report the issue through the university IT help desk or service portal. Include the device location and error message."
        ));

        faqs.add(new FAQ(
                "IT & Student Portal",
                "How can I access online learning resources or the learning management system?",
                "Access the learning-management system using the official university portal and your student credentials."
        ));

        faqs.add(new FAQ(
                "IT & Student Portal",
                "What should I do if I receive a suspicious university-related email or message?",
                "Do not open suspicious links or provide passwords or OTPs. Report the message through the university's official IT or security channel."
        ));


        // =====================================================
        // 76-85 : HOSTEL & CAMPUS
        // =====================================================

        faqs.add(new FAQ(
                "Hostel & Campus",
                "How can I apply for university hostel accommodation?",
                "Apply through the official hostel or student-services portal during the announced application period."
        ));

        faqs.add(new FAQ(
                "Hostel & Campus",
                "What documents are required for hostel registration?",
                "Hostel registration commonly requires student identification, admission proof, photographs and other forms specified by the hostel office."
        ));

        faqs.add(new FAQ(
                "Hostel & Campus",
                "How are hostel rooms allocated to students?",
                "Room allocation is based on the university's hostel policy and available capacity."
        ));

        faqs.add(new FAQ(
                "Hostel & Campus",
                "What are the hostel rules and quiet hours?",
                "Hostel rules cover quiet hours, visitors, safety, cleanliness and use of common facilities. Follow the current hostel handbook."
        ));

        faqs.add(new FAQ(
                "Hostel & Campus",
                "How can I report a hostel maintenance problem?",
                "Submit a maintenance request through the hostel portal, warden or maintenance desk. Include the room number."
        ));

        faqs.add(new FAQ(
                "Hostel & Campus",
                "What should I do if I lose my hostel room key or access card?",
                "Report a lost key or access card immediately to the hostel office or warden and follow the replacement procedure."
        ));

        faqs.add(new FAQ(
                "Hostel & Campus",
                "Can I request a hostel room change?",
                "Room changes depend on availability and hostel policy. Submit the prescribed request to the hostel office or warden."
        ));

        faqs.add(new FAQ(
                "Hostel & Campus",
                "What are the rules for visitors in the hostel?",
                "Visitors normally need to follow entry, identification and timing requirements specified by the hostel administration."
        ));

        faqs.add(new FAQ(
                "Hostel & Campus",
                "How can I report a campus facility problem?",
                "Report facility problems through the campus maintenance or help-desk system. Provide the exact location and description."
        ));

        faqs.add(new FAQ(
                "Hostel & Campus",
                "Whom should I contact in an emergency on campus?",
                "In an emergency, contact the university's designated campus-security or emergency number and inform authorised staff."
        ));


        // =====================================================
        // 86-95 : STUDENT SUPPORT & CAREER
        // =====================================================

        faqs.add(new FAQ(
                "Student Support & Career",
                "How can I contact my academic advisor?",
                "Academic advisors can usually be contacted through the department, programme office or student portal."
        ));

        faqs.add(new FAQ(
                "Student Support & Career",
                "What student counselling or wellbeing services are available?",
                "Universities may provide counselling, wellbeing, accessibility or student-support services. Check the official student-services portal."
        ));

        faqs.add(new FAQ(
                "Student Support & Career",
                "How can I report an academic or administrative issue?",
                "Use the university grievance, help-desk or student-support process. Describe the issue and attach relevant evidence."
        ));

        faqs.add(new FAQ(
                "Student Support & Career",
                "How can I create a support ticket through the student portal?",
                "Open the support or ticket section of the student portal, select the category, describe the issue and submit it."
        ));

        faqs.add(new FAQ(
                "Student Support & Career",
                "How can I check the status of a support ticket?",
                "Use the ticket-history or support section of the portal to view status and updates."
        ));

        faqs.add(new FAQ(
                "Student Support & Career",
                "How can I apply for an internship through the university?",
                "Check the career or placement office, internship portal and department requirements for eligibility and approvals."
        ));

        faqs.add(new FAQ(
                "Student Support & Career",
                "Where can I find information about campus placements?",
                "Placement information is normally published by the career or placement office through the student portal and official notices."
        ));

        faqs.add(new FAQ(
                "Student Support & Career",
                "How can I register for a career development or placement session?",
                "Register through the career or placement portal or the announced registration form."
        ));

        faqs.add(new FAQ(
                "Student Support & Career",
                "How can I obtain an official student certificate or bonafide certificate?",
                "Request a bonafide or student certificate through the student-services or registrar portal if online services are available."
        ));

        faqs.add(new FAQ(
                "Student Support & Career",
                "How can I request an official transcript?",
                "Transcript requests are normally submitted through the registrar, examination or student-services portal."
        ));


        // =====================================================
        // 96-100 : EXTRA STUDENT SERVICES
        // =====================================================

        faqs.add(new FAQ(
                "Student Support & Career",
                "How can I update my emergency contact details with the university?",
                "Update emergency-contact details through the student portal if self-service editing is enabled. Otherwise contact the student-records office."
        ));

        faqs.add(new FAQ(
                "Student Support & Career",
                "How can I join a student club or technical society?",
                "Check the student-affairs or student-activities portal for registered clubs and societies. Follow the published membership process."
        ));

        faqs.add(new FAQ(
                "Student Support & Career",
                "How can I register for a university workshop or seminar?",
                "Workshops and seminars are normally listed through the department, student-activities or career portal. Register using the official event form."
        ));

        faqs.add(new FAQ(
                "Student Support & Career",
                "Where can I find information about exchange or study-abroad opportunities?",
                "Exchange and study-abroad opportunities are normally published by the international-relations or global-engagement office."
        ));

        faqs.add(new FAQ(
                "Student Support & Career",
                "How can I request an official recommendation or reference letter?",
                "Recommendation letters are normally requested from an eligible faculty member or authorised office. Provide sufficient notice and share the purpose and deadline."
        ));

        return faqs;
    }
}