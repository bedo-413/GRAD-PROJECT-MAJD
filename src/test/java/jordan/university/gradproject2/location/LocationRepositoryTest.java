package jordan.university.gradproject2.location;

import jordan.university.gradproject2.enums.Faculty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class LocationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void testSaveLocation() {
        // Create a new location
        Location location = new Location();
        location.setLocationName("Test Location");
        location.setFaculty(Faculty.IT);

        // Save the location using repository
        Location savedLocation = locationRepository.save(location);

        // Flush changes to the database
        entityManager.flush();

        // Clear the persistence context
        entityManager.clear();

        // Retrieve the location from the database
        Location retrievedLocation = entityManager.find(Location.class, savedLocation.getLocationId());

        // Verify the location was saved correctly
        assertNotNull(retrievedLocation);
        assertEquals("Test Location", retrievedLocation.getLocationName());
        assertEquals(Faculty.IT, retrievedLocation.getFaculty());
    }

    @Test
    public void testFindLocationById() {
        // Create a location
        Location location = new Location();
        location.setLocationName("Another Test Location");
        location.setFaculty(Faculty.ENGINEERING);

        // Persist the location using entity manager
        Location persistedLocation = entityManager.persistFlushFind(location);

        // Clear the persistence context
        entityManager.clear();

        // Find the location by ID using repository
        Optional<Location> foundLocation = locationRepository.findById(persistedLocation.getLocationId());

        // Verify the location was found
        assertTrue(foundLocation.isPresent());
        assertEquals("Another Test Location", foundLocation.get().getLocationName());
        assertEquals(Faculty.ENGINEERING, foundLocation.get().getFaculty());
    }

    @Test
    public void testFindAllLocations() {
        // Clear any existing data
        entityManager.getEntityManager().createQuery("DELETE FROM Location").executeUpdate();
        entityManager.flush();

        // Create and persist multiple locations
        Location location1 = new Location();
        location1.setLocationName("Location 1");
        location1.setFaculty(Faculty.SCIENCE);
        entityManager.persist(location1);

        Location location2 = new Location();
        location2.setLocationName("Location 2");
        location2.setFaculty(Faculty.BUSINESS);
        entityManager.persist(location2);

        entityManager.flush();
        entityManager.clear();

        // Find all locations using repository
        List<Location> locations = locationRepository.findAll();

        // Verify all locations were found
        assertEquals(2, locations.size());
    }

    @Test
    public void testUpdateLocation() {
        // Create and persist a location
        Location location = new Location();
        location.setLocationName("Original Name");
        location.setFaculty(Faculty.LAW);
        Location persistedLocation = entityManager.persistFlushFind(location);

        // Clear the persistence context
        entityManager.clear();

        // Find the location, update it, and save it using repository
        Location locationToUpdate = locationRepository.findById(persistedLocation.getLocationId()).get();
        locationToUpdate.setLocationName("Updated Name");
        locationToUpdate.setFaculty(Faculty.MEDICINE);
        locationRepository.save(locationToUpdate);

        // Flush changes and clear persistence context
        entityManager.flush();
        entityManager.clear();

        // Retrieve the updated location from database
        Location updatedLocation = entityManager.find(Location.class, persistedLocation.getLocationId());

        // Verify the location was updated
        assertNotNull(updatedLocation);
        assertEquals(persistedLocation.getLocationId(), updatedLocation.getLocationId());
        assertEquals("Updated Name", updatedLocation.getLocationName());
        assertEquals(Faculty.MEDICINE, updatedLocation.getFaculty());
    }

    @Test
    public void testDeleteLocation() {
        // Create and persist a location
        Location location = new Location();
        location.setLocationName("Location to Delete");
        location.setFaculty(Faculty.ARTS);
        Location persistedLocation = entityManager.persistFlushFind(location);

        // Get the ID for later verification
        Long locationId = persistedLocation.getLocationId();

        // Clear the persistence context
        entityManager.clear();

        // Delete the location using repository
        locationRepository.deleteById(locationId);

        // Flush changes
        entityManager.flush();

        // Verify the location was deleted
        Location deletedLocation = entityManager.find(Location.class, locationId);
        assertNull(deletedLocation);

        // Also verify using repository
        Optional<Location> optionalLocation = locationRepository.findById(locationId);
        assertFalse(optionalLocation.isPresent());
    }
}
