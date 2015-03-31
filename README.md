# PersistentUUIDs

The UUIDs used for UsagePoint must be constant (should also be constant for ReadingType, LocalTimeParameters, MeterReading). This is necessary so that a user or third party can recognize updates and extensions to the data sets from one Green Button file to the next. The identities of these resources are established when the account is created and doesn’t change over the life of the account. 

Those for each IntervalBlock created should get a new random UUID value. Also, for each new ElectricPowerUsageSummary and ElectricPowerQualitySummary a new UUID is created. If updates to these resources are to be provided, the UUIDs must be preserved or otherwise regenerated.

Generate the UUIDs using RFC4122 (http://www.ietf.org/rfc/rfc4122.txt) which is the recognized standard.
 
In making persistent UUIDs that do not need to be stored, the form to use is a one-way hash based on SHA-1 which is a secure non-reversible hash function. The construction of the UUID is called version 5 (note the method below allows you to compute them each time; the algorithm for constructing uuid is in section 4.3 of the RFC). 

   The requirements for these types of UUIDs are as follows:

- The UUIDs generated at different times from the same name in the same namespace MUST be equal.
- The UUIDs generated from two different names in the same namespace should be different (with very high probability).
- The UUIDs generated from the same name in two different namespaces should be different with (very high probability).
- If two UUIDs that were generated from names are equal, then they were generated from the same name in the same namespace (with very high probability).

This method of generating a UUID combines a scheme, a namespace, and a name to create a globally unique string that can be formed into a UUID. It is called “version 5” of the RFC4122 that allows the creation of a UUID based on three data elements:
- A namespaceUUIDType – for example NameSpace_URL from appendix C of the standard allows you to use a url for the namespace part (for this form, the NameSpace_URL value is 6ba7b811-9dad-11d1-80b4-00c04fd430c8 
- A namespace – if the type is NameSpace_URL, then an example namespace would be “services.greenbuttondata.org”
- A name – a unique name within the namespace. 

The form of the UUID recommended for use in ESPI is:

	urn:uuid:xxxxxxxx-xxxx-Mxxx-Nxxx-xxxxxxxxxxxx
 
(where M and N are defined in the standard and x are hexadecimal digits):

The value of M is 5 for version 5. The value of N is the most significant two bits of that character must be 0b10. That is values of 8,9,a,b are valid values of hex nibble N.

To use this scheme:
1.	consider meterId as the meter id (another id that is constant with respect to the meter that corresponds to the UsagePoint will do). Then create the “names” for the persistent UUIDs for use in your Green Button installation:
usagePointName = meterId
meterReadingName = meterId + “mrWh” this will be a constant for MeterReadings of this UsagePoint of the Wh readings.
readingTypeName = readingTypeWh  this will be a constant for all GB data you make of that ReadingType
localTimeParamtersName = localTimeParamtersPT  this will be a constant for all GB data you make that is pacific time zone
2.	Then generate the corresponding UUIDs by applying SHA-1 to the desired concatenated string:
3.	Generate the bytes of the UUID = SHA1(namespaceUUIDType + namespace + name) where each term is ordered sequence of bytes concatenated (note leave out formatting and separating characters such as the ‘-‘ in the namespaceUUIDType).
4.	Then, set the values of M and N:
M is 13th nibble
N is upper 2 bits of 17th nibble
5.	Format the string

The software in this project illustrates how to implement the algorithm.