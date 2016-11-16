import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws Exception {
	    DNSProtos.Address.Builder localhost = DNSProtos.Address.newBuilder().setDomain("localhost").setIp("0.0.0.0");
	    DNSProtos.Address.KnownAddress pl = DNSProtos.Address.KnownAddress.newBuilder().setDmn("pl").setIps("1.2.3.4").build();
	    DNSProtos.Address.KnownAddress com = DNSProtos.Address.KnownAddress.newBuilder().setDmn("com").setIps("5.6.7.8").build();
	    DNSProtos.Address.KnownAddress org = DNSProtos.Address.KnownAddress.newBuilder().setDmn("org").setIps("9.10.11.12").build();
	    localhost.addKnownaddress(pl);
	    localhost.addKnownaddress(com);
	    localhost.addKnownaddress(org);
	    localhost.build();
	    
	    DNSProtos.Address.Builder dot_pl = DNSProtos.Address.newBuilder().setDomain("pl").setIp("1.2.3.4");
	    DNSProtos.Address.KnownAddress wp_pl = DNSProtos.Address.KnownAddress.newBuilder().setDmn("wp.pl").setIps("13.14.15.16").build();
	    DNSProtos.Address.KnownAddress poznan_pl = DNSProtos.Address.KnownAddress.newBuilder().setDmn("poznan.pl").setIps("17.18.19.20").build();
	    dot_pl.addKnownaddress(wp_pl);
	    dot_pl.addKnownaddress(poznan_pl);
	    dot_pl.build();
	    
	    DNSProtos.Address.Builder dot_poznan_pl= DNSProtos.Address.newBuilder().setDomain("poznan.pl").setIp("17.18.19.20");
	    DNSProtos.Address.KnownAddress put_poznan_pl = DNSProtos.Address.KnownAddress.newBuilder().setDmn("put.poznan.pl").setIps("21.22.23.24").build();
	    dot_poznan_pl.addKnownaddress(put_poznan_pl);
	    
	    DNSProtos.AddressList.Builder addressList = DNSProtos.AddressList.newBuilder().addAddress(localhost).addAddress(dot_pl).addAddress(dot_poznan_pl);
	    
	    FileOutputStream output = new FileOutputStream("dnstest.txt");
	    addressList.build().writeTo(output);
	    output.close();
	    
	    //DNSProtos.AddressList addressRead = DNSProtos.AddressList.parseFrom(new FileInputStream("dnstest.txt"));
	    //PRINT(addressRead);
	    checkserver("poznan.pl", localhost);
	}
	
	static void PRINT(DNSProtos.AddressList addressList) {
		for (DNSProtos.Address address : addressList.getAddressList()) {
			System.out.println("======================================");
			System.out.println("Domain: " + address.getDomain());
			System.out.println("IP: " + address.getIp());
			System.out.println("Known addresses:");
			
			for (DNSProtos.Address.KnownAddress knwadd : address.getKnownaddressList()) {
				System.out.println("\t================================");
				System.out.println("\tDomain: " + knwadd.getDmn());
				System.out.println("\tIP: " + knwadd.getIps());
				
			}
		}
	}
	
	static void checkserver(String query, DNSProtos.Address.Builder address) {
		System.out.println("---------------------------------------------");
		
		boolean found = false;
		String foundAddress = null;
		
		for (DNSProtos.Address.KnownAddress knwadr : address.getKnownaddressList()) {
			
			System.out.println("======================================");
			System.out.println("Domain: " + knwadr.getDmn());
			System.out.println("IP: " + knwadr.getIps());
			System.out.println("Query: " + query);
			if (knwadr.getDmn().equals(query)) {
				System.out.println("OK. -> " + knwadr.getDmn() + " " + knwadr.getIps());
				found = true;
				break;
			}		
					
		}
		
		// Split query using "." 
		String[] splitQuery = query.split("\\.");
		String lastPart = splitQuery[splitQuery.length - 1];
		System.out.println("\nLast part: " + lastPart);
		
		for (DNSProtos.Address.KnownAddress knwadr : address.getKnownaddressList()) {
			System.out.println("======================================");
			System.out.println("Domain: " + knwadr.getDmn());
			System.out.println("IP: " + knwadr.getIps());
			System.out.println("Query: " + lastPart);
			if (knwadr.getDmn().equals(lastPart)) {
				System.out.println("OK. -> " + knwadr.getDmn() + " " + knwadr.getIps());
				foundAddress = knwadr.getDmn() + ";" + knwadr.getIps();
				found = true;
				break;
			}		
		}
		
		if (found) {
			System.out.println("*******************************");
			System.out.println("Found address");
			System.out.println(foundAddress);
			String[] splitFound = foundAddress.split(";");
			System.out.println("Domain: " + splitFound[0]);
			System.out.println("IP: " + splitFound[1]);
		}
		else {
			System.out.println("Sorry. Your query was not found");
		}
	}
}
