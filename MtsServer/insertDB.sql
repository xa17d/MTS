INSERT INTO "PUBLIC"."PATIENT"
( "ID", "GUID", "VERSION" )
VALUES ( 1, '204c4fea-9827-4614-9793-f7c8b3c98e18', 2 );

INSERT INTO "PUBLIC"."PATIENTVERSION"
( "ID", "PATIENT", "VERSION", "NAMEGIVEN", "NAMEFAMILY", "BIRTHTIME", "GENDER", "WALKABLE", "RESPIRATION", "PERFUSION", "MENTALSTATUS", "PHASEOFLIFE", "PLACEPOSITION", "URGENCY", "BLOODPRESSURESYSTOLIC", "BLOODPRESSUREDIASTOLIC", "PULSE", "READYFORTRANSPORT", "HOSPITAL", "HEALTHINSURANCE", "TREATMENT" )
VALUES ( 1, 1, 1, 'Given', 'Family', '1990-09-18', 'maennlich', TRUE, 'notSpecified', 'notSpecified', 'notSpecified', 'notSpecified', 'notSpecified', 1, 2, 3, 4, 5, 'notSpecified', 'notSpecified', 'notSpecified');

INSERT INTO "PUBLIC"."PATIENTVERSION"
( "ID", "PATIENT", "VERSION", "NAMEGIVEN", "NAMEFAMILY", "BIRTHTIME", "GENDER", "CATEGORY", "WALKABLE", "RESPIRATION", "PERFUSION", "MENTALSTATUS", "PHASEOFLIFE", "PLACEPOSITION", "URGENCY", "BLOODPRESSURESYSTOLIC", "BLOODPRESSUREDIASTOLIC", "PULSE", "READYFORTRANSPORT", "HOSPITAL", "HEALTHINSURANCE", "TREATMENT" )
VALUES ( 2, 1, 2, 'Given2', 'Family2', '1990-09-18', 'maennlich', 'notSpecified', TRUE, 'notSpecified', 'notSpecified', 'notSpecified', 'notSpecified', 'notSpecified', 1, 2, 3, 4, 5, 'notSpecified', 'notSpecified', 'notSpecified');