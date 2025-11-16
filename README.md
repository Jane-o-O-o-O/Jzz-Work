Comprehensive Application - Paginated Query Requirements:

Technical Requirements: Use Tomcat 10.1.48; Programming Language: Java

1. Query Functionality

Combined Query: Supports querying database data by freely combining multiple conditions; partial fields support fuzzy queries

Pagination Query: When the database contains a large number of records, pagination divides the data into multiple pages to improve page loading speed and user experience. Users navigate through pages to view specific data. The number of records displayed per page is freely configurable.

Sorting Output: Sort query results in ascending or descending order based on a selected field.

Result Display: Results are displayed in a table.

Alternating Row Colors: Alternate row colors in the table for enhanced visual appeal and readability.

Row Highlighting: Highlight selected rows with a distinct background color when hovering over them for increased visibility.

Batch Selection: Add checkboxes to the first column for selecting rows. Selected rows are highlighted with a distinct background color for subsequent batch operations.



2. Deletion Functions

Single Deletion: A delete button added to the last column of the table. Clicking it deletes the current row.

Batch Deletion: Checkboxes added to the first column of the table. Clicking the toolbar button deletes all selected rows at once.



3. Edit Functionality

Single Edit: Add an edit button to the last column of the table. Clicking it opens a pop-up window on the current page displaying the current row's data. Users can modify as needed, save changes, close the pop-up, and refresh the table data.



4. Add Functionality

Click the toolbar button to open a popup window on the current page. Enter new data fields, save changes, close the popup, and refresh the table data.

Below is the lesson plan:

1. CRUD Fundamentals

CRUD represents four core data operations:

C – Create
R – Retrieve
U – Update

D – Delete

In this lesson, all CRUD operations are performed asynchronously via AJAX. This eliminates the need for full page refreshes, delivering a user experience closer to the smooth operation of C/S applications.

2. Functional Requirements Analysis
2.1 Query Functionality

The query module must implement the following capabilities:

Combined Queries: Supports freely combining multiple field conditions, with partial fields enabling fuzzy searches
(e.g., fuzzy name matching).

Pagination: Return results in paginated sets for large datasets, improving loading speed. Users can select the number of records per page.

Sorting Output: Allow sorting by any field in ascending or descending order.

Results Displayed in Table: All query results are presented in a tabular format.

Table Interaction Optimization

To enhance readability and user experience, the following is required:

Alternating Row Colors: Different background colors for odd and even rows.

Row highlighting: Hovering over a row highlights it.

Batch selection: A checkbox column enables bulk actions like deletion. Selected rows should be highlighted.

2.2 Deletion Functionality

Deletion includes two types:

Single-row deletion: Clicking a row's delete button sends an AJAX request to remove that record.

Batch deletion: Select multiple records and submit them to the backend batch deletion interface.

After successful deletion, no page refresh is required; only the current table data is updated.

2.3 Edit Functionality

The edit process includes:

Click “Edit” on a row

Inline input fields open, allowing modification of corresponding fields

Click “Save” to submit changes to the backend via AJAX

After success, the row display is updated

No page redirection or full refresh is needed.

2.4 Addition Functionality (Create)

New data flow:

Fill out the new form at the top of the page or in a pop-up window

Click the “Add” button

Use AJAX to submit data to the backend in JSON or form format

Backend addition successful → Append a new row to the page or refresh the list

3. System Design

System design primarily encompasses four major components: page design, interaction design, controller design, and DAO design.

3.1 Page Design (Frontend)

Core page elements include:

Query input field and combined query area

Add/Batch Delete button area

Pagination navigation

Sorting buttons (e.g., header click sorting)

List display table

Inline editing input fields

CSS configurations for highlighting, alternating row colors, etc.

The overall page layout balances usability and aesthetics.

3.2 Interaction Design
3.2.1 Human-Computer Interaction (Frontend Behaviors)

Primary interactions:

Input field entry/change → Automatically updates search criteria

Mouse hover over row → Row highlighting

Checkbox selection → Row style change

Sort button click → Data re-fetch

Pagination button click → Load corresponding page data (AJAX)

Edit button click → Inline editing mode activated

All interactions are dynamically rendered on the frontend without page reloads.

3.2.2 Client-Server Interaction (Frontend/Backend APIs)

The backend primarily provides the following interfaces (actions invoked via AJAX):

Function    Request Method    Description
Query    GET/POST    Return paginated, sorted data list
Insert    POST    Add new record
Update    POST/PUT    Modify a specific record
Delete    POST/DELETE    Delete single or multiple records

Client Tasks:

Send AJAX requests (XMLHttpRequest / jQuery AJAX)

Receive JSON responses

Update page DOM based on response results

3.3 Controller Layer Design (Controller)

Controllers implement backend business logic routing, e.g.:

/query.do
/add.do
/update.do
/delete.do
/deleteBatch.do

The Controller is responsible for:

Receiving frontend parameters

Invoking the Service

Returning JSON-formatted data (e.g., paginated results, success/failure status)

3.4 DAO Layer Design

The DAO layer primarily implements CRUD operations through database interactions, such as:

selectByCondition

insert

updateById

deleteById

deleteBatch

Ensuring stable and secure operations at the data persistence layer.


Translated with DeepL.com (free version)
