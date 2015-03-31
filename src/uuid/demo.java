package uuid;

import uuid.UuidESPI;

public class demo {

	public static void main(String[] args) {
		
		String v5uuid = UuidESPI.generate("services.greenbuttondata.org", "DataCustodian/espi/1_1/resource/Subscription/1");
		System.out.printf("Type 5 UUID: %s\n", v5uuid);
	}
}
