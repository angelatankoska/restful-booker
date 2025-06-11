# Restful Booker API Tests

This project contains automated API tests for the [Restful Booker](https://restful-booker.herokuapp.com/) service using Java, RestAssured, and TestNG.

## Useful Links

- API Documentation: [https://restful-booker.herokuapp.com/apidoc/index.html](https://restful-booker.herokuapp.com/apidoc/index.html)
- Postman Collection: [https://www.postman.com/automation-in-testing/restful-booker-collections/overview](https://www.postman.com/automation-in-testing/restful-booker-collections/overview)

## Covered Test Scenarios

All tests are located in `BookingTests.java`:

- `testCreateAndGetBooking` – creates a booking and then retrieves it by ID.
- `testGetAllBookingIds` – checks that booking IDs can be fetched and are not empty.
- `testUpdateBooking` – performs a full update on an existing booking.
- `testPartialUpdateBooking` – updates only the first name of an existing booking.
- `testDeleteBooking` – deletes a booking and verifies it's no longer retrievable.
- `testCreateBookingWithInvalidData` – attempts to create a booking with invalid fields (e.g., negative price, null names).
- `testGetNonExistentBooking` – tries to fetch a booking that doesn't exist (should return 404).
- `testBookingDateRangeValidation` – tests invalid date ranges (e.g., checkout before checkin).

## Technologies Used

- **Java 17**
- **RestAssured**
- **TestNG**
- **AssertJ**
- **Maven**
- **IntelliJ IDEA**

## How to Run the Tests

1. Clone the repository:
   ```bash
   git clone https://github.com/angelatankoska/restful-booker.git
   cd restful-booker
2. Run the tests directly from IntelliJ IDEA.

