package com.kdu.IBE.constants;

public class RepositoryQuery {
    public static final String CONCURRENCY_HANDLING_QUERY = "SELECT ra.availability_id, ra.room_id  \n" +
            "FROM room_availability ra \n" +
            "INNER JOIN room r ON ra.room_id = r.room_id  \n" +
            "WHERE r.room_type_id = ?1 \n" +
            "  AND ra.booking_id = 0 \n" +
            "  AND \"date\" >= CAST(?2 AS DATE) \n" +
            "  AND \"date\" <= CAST(?3 AS DATE) \n" +
            "  AND ra.room_id NOT IN (\n" +
            "    SELECT ra2.room_id \n" +
            "    FROM room_availability ra2 \n" +
            "    INNER JOIN room r2 ON ra2.room_id = r2.room_id  \n" +
            "    WHERE r2.room_type_id = ?1 \n" +
            "      AND ra2.booking_id > 0 \n" +
            "      AND \"date\" >= CAST(?2 AS DATE) \n" +
            "      AND \"date\" <= CAST(?3 AS DATE)\n" +
            "  ) \n" +
            "ORDER BY ra.room_id \n" +
            "LIMIT ?4 \n" +
            "for UPDATE SKIP LOCKED;";

}


