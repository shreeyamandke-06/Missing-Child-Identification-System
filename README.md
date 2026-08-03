# Missing Child Identification System

A Java-based desktop application developed using Java Swing and MySQL to help manage and track missing child cases.

## Features

- Register missing children
- Register parents
- Report missing child cases
- Search for missing children
- View all registered cases
- Update case status
- View case status history
- Register found children
- Match found children with existing cases
- Manage locations

---

## Technologies Used

- Java
- Java Swing
- JDBC
- MySQL
- Eclipse IDE

---

## Database Tables

- person
- child
- parent
- address
- location
- lost_case
- found_child
- case_status_log

---

## Project Structure

```
src/
└── dbms_proj/
    ├── AdminDashboardUI.java
    ├── DBConnection.java
    ├── RegisterChildUI.java
    ├── RegisterParentUI.java
    ├── ReportMissingUI.java
    ├── SearchMissingChildUI.java
    ├── ViewMissingCasesUI.java
    ├── UpdateCaseStatusUI.java
    ├── StatusHistoryUI.java
    ├── RegisterFoundChildUI.java
    ├── MatchCaseUI.java
    └── ManageLocationsUI.java
```

---

## How to run the project

1. Clone the repository.

```bash
git clone <repository-url>
```

2. Create the MySQL database.

```sql
CREATE DATABASE dbms_proj;
```

3. Import the database schema.

4. Update the database username and password inside `DBConnection.java`.

5. Run `AdminDashboardUI.java`.

---

## Future Improvements

- Admin authentication
- Face recognition integration
- Automated matching
- Email notifications
- Cloud deployment

---

## Author

Shreeya Mandke
