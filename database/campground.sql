select s.site_id,s.campground_id,s.site_number,s.max_occupancy,s.accessible,s.max_rv_length,s.utilities,c.daily_fee 
FROM site s JOIN reservation r ON r.site_id=s.site_id JOIN campground c ON c.campground_id = s.campground_id 
WHERE s.site_id not in (
SELECT reservation.site_id from reservation
WHERE (reservation.from_date < ? and reservation.to_date > ? ) OR (reservation.from_date > ?
AND reservation.to_date < ? ) OR (reservation.from_date = ? AND reservation.to_date = ?)
)
 AND site.campground_id = ? LIMIT 5
