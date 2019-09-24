package rentalbike;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import domain.Bike;
import domain.FamilyRental;
import domain.InvalidFamilyRentalException;
import domain.InvalidRentalBikeException;
import domain.Rental;
import domain.RentalDaily;
import domain.RentalHourly;
import domain.RentalWeekly;
import junit.framework.TestCase;


public class RentalTests extends TestCase {

	private static final String THE_BIKE_IS_NOT_AVAILABLE = "The bike is not available";
	private Long hourlyExpectedPrice = 5l;
	private Long dailyExpectedPrice = 20l;
	private Long weeklyExpectedPrice = 60l;
	

	@Test
	public void testHourRental() throws InvalidRentalBikeException {        
		Rental rental = new Rental(createBike(true), new RentalHourly());
		assertEquals(rental.calculatePromotion(), BigDecimal.valueOf(hourlyExpectedPrice));
	}

	@Test
	public void testHourRentalUnavailableBike() {        
		Rental rental = null;
		try {
			rental = new Rental(createBike(false), new RentalHourly());
		} catch (InvalidRentalBikeException e) {
			assertEquals(e.getMessage(), THE_BIKE_IS_NOT_AVAILABLE);
		}
		assertNull(rental);
	}    

	@Test
	public void testDayRental() throws InvalidRentalBikeException {
		Rental rental = new Rental(createBike(true), new RentalDaily());
		assertEquals(rental.calculatePromotion(), BigDecimal.valueOf(dailyExpectedPrice));
	}

	@Test
	public void testDailyRentalUnavailableBike() {        
		Rental rental = null;
		try {
			rental = new Rental(createBike(false), new RentalDaily());
		} catch (InvalidRentalBikeException e) {
			assertEquals(e.getMessage(), THE_BIKE_IS_NOT_AVAILABLE);
		}
		assertNull(rental);
	}       

	@Test
	public void testWeekRental() throws InvalidRentalBikeException {
		Rental rental = new Rental(createBike(true), new RentalWeekly());
		assertEquals(rental.calculatePromotion(), BigDecimal.valueOf(weeklyExpectedPrice));
	}

	@Test
	public void testWeeklyRentalUnavailableBike() {        
		Rental rental = null;
		try {
			rental = new Rental(createBike(false), new RentalWeekly());
		} catch (InvalidRentalBikeException e) {
			assertEquals(e.getMessage(), THE_BIKE_IS_NOT_AVAILABLE);
		}
		assertNull(rental);
	}    


	@Test
	public void testFamilyRental() throws InvalidRentalBikeException, InvalidFamilyRentalException {
		List<Rental> rentals = new ArrayList<>();
		rentals.add(new Rental(createBike(true), new RentalWeekly()));
		rentals.add(new Rental(createBike(true), new RentalHourly()));
		rentals.add(new Rental(createBike(true), new RentalDaily()));
		FamilyRental rental = new FamilyRental(rentals);
		assertEquals(rental.calculatePromotion(), new BigDecimal(59.50).setScale(2));
	}

	@Test
	public void testFamilyRentalLessRentals() throws InvalidRentalBikeException {
		List<Rental> rentals = new ArrayList<>();
		rentals.add(new Rental(createBike(true), new RentalWeekly()));
		rentals.add(new Rental(createBike(true), new RentalHourly()));
		FamilyRental rental = null;
		try {
			rental = new FamilyRental(rentals);
		} catch (InvalidFamilyRentalException e) {
			assertEquals(e.getMessage(), "The rental is invalid - The family promotion includes between 3 and 5 bikes");
		}
		assertNull(rental);
	}

	@Test
	public void testFamilyRentalMoreRentals() throws InvalidRentalBikeException {
		List<Rental> rentals = new ArrayList<>();
		rentals.add(new Rental(createBike(true), new RentalWeekly()));
		rentals.add(new Rental(createBike(true), new RentalHourly()));
		rentals.add(new Rental(createBike(true), new RentalHourly()));
		rentals.add(new Rental(createBike(true), new RentalHourly()));
		rentals.add(new Rental(createBike(true), new RentalHourly()));
		rentals.add(new Rental(createBike(true), new RentalHourly()));
		FamilyRental rental = null;
		try {
			rental = new FamilyRental(rentals);
		} catch (InvalidFamilyRentalException e) {
			assertEquals(e.getMessage(), "The rental is invalid - The family promotion includes between 3 and 5 bikes");
		}
		assertNull(rental);
	}


	private Bike createBike(boolean available) {
		return new Bike("Montain", "bike all terrain", available);
	}
	

}
