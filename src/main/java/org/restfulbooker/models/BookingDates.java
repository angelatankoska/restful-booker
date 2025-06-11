package org.restfulbooker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class BookingDates {


    private String checkin;
    private String checkout;

    // Getters
    public String getCheckin() { return checkin; }
    public String getCheckout() { return checkout; }

    // Builder
    public static class Builder {
        private String checkin;
        private String checkout;

        public Builder checkin(String checkin) {
            this.checkin = checkin;
            return this;
        }

        public Builder checkout(String checkout) {
            this.checkout = checkout;
            return this;
        }

        public BookingDates build() {
            BookingDates dates = new BookingDates();
            dates.checkin = this.checkin;
            dates.checkout = this.checkout;
            return dates;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}

