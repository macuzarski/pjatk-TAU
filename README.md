# Selenium Testing Scenarios

# Prerequisites: 
Depending on browser version,
    driver executables - matching your browser version - accessible in the project (not included in this repo)

## 1. Google Search
**Objective**: Test Google search functionality
- **Steps**:
    1. Open Google homepage
    2. Verify the title contains "Google"
    3. Enter a search term (e.g., "Selenium with Python")
    4. Submit the search
    5. Verify results page title contains "Selenium with Python"
    6. Verify results are displayed
    7. Verify that at least one result contains "Selenium"
    8. Verify the URL includes `/search?q=Selenium+with+Python`

## 2. Amazon Shopping Cart
**Objective**: Test Amazon’s cart functionality
- **Steps**:
    1. Open Amazon homepage
    2. Verify the title contains "Amazon"
    3. Search for an item (e.g., "headphones")
    4. Add the first result to the cart
    5. Go to the cart
    6. Verify that the cart contains one item
    7. Verify that the item title matches the search term
    8. Verify that the cart subtotal is displayed

## 3. Wikipedia Content Navigation
**Objective**: Test content navigation on Wikipedia
- **Steps**:
    1. Open Wikipedia homepage
    2. Verify the title contains "Wikipedia"
    3. Enter a search term (e.g., "Python programming")
    4. Verify the search results include the article "Python (programming language)"
    5. Open the article
    6. Verify that the first heading is "Python (programming language)"
    7. Check the existence of the "History" section
    8. Verify external links section exists

## 4. LinkedIn Job Search and View
**Objective**: Test LinkedIn's job search and view functionality.
- **Steps**:
  1. Open LinkedIn's job search page.
  2. Verify the title contains "Jobs".
  3. Enter a job title (e.g., "Software Engineer") in the search box.
  4. Enter a location (e.g., "San Francisco, CA") in the location box.
  5. Submit the search.
  6. Verify that job results are displayed.
  7. Verify the first job listing includes a job title, company name, and location.
  8. Confirm the presence of a "Save" button on the first job listing.