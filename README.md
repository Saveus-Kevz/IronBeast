IRON BEAST GYM MANAGEMENT SYSTEM
================================

A. COMPILE & RUN
---------------
1. Open terminal in project folder (with "src" folder)
2. Compile: javac -d bin src/**/*.java
3. Run: java -cp bin app.IronBeast

B. PROJECT SUMMARY
------------------
Console-based gym member management system. Tracks members, 
memberships, renewals, and member information with file storage.

FEATURES:
- Register/update/delete members
- View active/inactive members
- Renew/upgrade memberships
- Search members by name/ID
- Data saved to CSV file
- Input validation & duplicate detection

C. HOW TO USE
-------------
MAIN MENU OPTIONS:
1. Register New Member - Add new member with details
2. Show Active Members - View current members
3. Show Inactive Members - View expired members
4. Renew Membership - Extend/upgrade membership
5. Update Member Info - Modify member details
6. Delete Member - Remove member permanently
7. Member Lookup - Search by name or ID
99. Exit - Close application

DATA FORMATS:
- Birthdate: YYYY-MM-DD (must be 14+ years old)
- Phone: 09XX-XXX-XXXX or 02-XXXX-XXXX
- Membership: BASIC(1mo), PREMIUM(3mo), VIP(12mo)

DATA: Saved in src/storage/members.csv