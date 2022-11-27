import java.util.Scanner;
public class BusReservationAndTicketingSystem{
    public static void main(String args[]) {
    Scanner sn = new Scanner(System.in);
        String search,again,choice;
        int index,yn=1,size=0,check=0;
        int available[] = new int[6];
        String checkName[] = new String[80];
        for(int o=1; o<=5; o++){
            available[o]=20;
  	    }
        Passenger[] ps = new Passenger[80];
        Billing billing = new Billing(); 
        MainMenu menu = new MainMenu();
        View view = new View();
        do{
            menu.menu();
            System.out.print("ENTER CHOICE: ");
            choice=sn.next();
            if(choice.equals("1")){
                menu.menuDestination(available);
            }

            else if(choice.equals("2")){
                menu.menuDestination(available);
                ps[size] = new Passenger();
                System.out.print("ENTER PASSENGER'S NAME: ");
                search = sn.next();
                checkName[size] = search;
                check = 0;
                for(int count=0;count<=size;count++){
                    if(search.equalsIgnoreCase(checkName[count])){
                        ps[size].setName(search);
                        check = 1;
                        break;
                    }
                }
                if(check == 0){
                    System.out.println("Sorry, Passenger's name have already used!");
                    }else{
                    index = ps[size].setDestination();
                    if((available[1]==0)&&(available[2]==0)&&(available[3]==0)&&(available[4]==0)&&(available[5]==0)){
                        System.out.println("Sorry, We don't  have available seats for all Destination!");
                    }else{
                        available[index] = ps[size].howManyPassenger(available);
                    }
                }
                    size++;
            }

            else if(choice.equals("3")){
                System.out.print("ENTER PASSENGER'S NAME: "); 
                search = sn.next();
                check = 0;
                for(int count=0;count<size;count++){
                    if(search.equalsIgnoreCase(ps[count].name)){
                        billing.search(ps[count].name, ps[count].destination, ps[count].facePrice, ps[count].noOfPassenger, ps[count].status, ps[count].pay, ps[count].change, ps[count].totalFarePrice);
                        ps[count].change = billing.getChange();
                        ps[count].status = billing.getStatus();
                        ps[count].pay = billing.getPay();
                        check = 1;
                        break;
                    }
                }
                if(check == 0){
                    System.out.println("Passenger's Name not found!");
                }
            }

            else if(choice.equals("4")){
                System.out.print("SEARCH PASSENGER'S NAME: ");
                search = sn.next();
                check = 0;
                for(int count=0;count<size;count++){
                    if(search.equalsIgnoreCase(ps[count].name)){
                        view.search(ps[count].name, ps[count].destination, ps[count].facePrice, ps[count].noOfPassenger, ps[count].status, ps[count].pay, ps[count].change, ps[count].totalFarePrice);    
                        check = 1;
                        break;
                    }
                }
                if(check == 0){
                    System.out.println("Passenger's Name not found!");
                }
            }

            else if(choice.equals("5")){
                break;
            }
            do{
                System.out.print("Do you wish to continue with this transaction? [Y/N]: ");
                again=sn.next();
                if(again.equalsIgnoreCase("y")){
                    yn=1;
                }else if (again.equalsIgnoreCase("n")){
                    yn=0;								
                }else{
                    System.out.println("\nInvalid input!\n");
                }
            }while(yn == 1 && yn == 0);
        }while(yn==1);
    }
}

class Passenger{
    Scanner sn = new Scanner(System.in);
    String name, destination, status; 
    double facePrice, pay, change, totalFarePrice; 
    int noOfPassenger, destinationChoice=0; 
    String dest[] = { " ", "Su-ngai Kolok", "Narathiwat", "Yala", "Naphradu", "Chana - Hatyai"};
    double fare[] = { 0, 150,120,25,60,120};
       
    public void setName(String name){
        this.name = name;            
    }
    public int setDestination(){
        System.out.print("ENTER DESTINATION [number]: ");
        destinationChoice = sn.nextInt();
     	if(destinationChoice<1 || destinationChoice>5){
     		System.out.println("Invalid Input!");
     	}else{
            destination = dest[destinationChoice];
            facePrice = fare[destinationChoice];
            status = "not_paid";
        }
        return destinationChoice;
    }
    public int howManyPassenger(int available[]){
        System.out.print("HOW MANY PASSENGERS ARE YOU?: ");
    	noOfPassenger = sn.nextInt();    
                if((available[destinationChoice]<0) || (noOfPassenger>available[destinationChoice])){
					System.out.print("Sorry, We don't have seat available for " + noOfPassenger +" person\n");
					System.out.print("We only have " +available[destinationChoice] +" seat available\n");
                }
				else{
				    available[destinationChoice] = available[destinationChoice]-noOfPassenger;
                    totalFarePrice = fare[destinationChoice] * noOfPassenger;
				}            
        return available[destinationChoice];
    }
}

class Billing{
    Scanner sn = new Scanner(System.in);
    public void search(String name, String destination, double facePrice, int noOfPassenger, String status, double pay, double change, double totalFarePrice){
		System.out.println("***************************************");
		System.out.println("**        PASSENGER'S DETAILS        **");
		System.out.println("***************************************");
		System.out.println("PASSENGER'S NAME: " + name);
		System.out.println("PASSENGER'S DESTINATION : " + destination);
		System.out.println("FARE PRICE: " + facePrice + " Bath");
		System.out.println("NO. OF PASSENGERS: " + noOfPassenger);
		System.out.println("***************************************");
		System.out.println("***************************************");
        pay(facePrice, status, pay, change, totalFarePrice);
    }
    double change;
    double pay;
    String status;
    public void pay(double facePrice,String status, double pay, double change, double totalFarePrice){
        this.change = totalFarePrice;
        if(status.equals("paid")){
            System.out.println("Passenger's Already Paid!");
        }else{
            this.status="not_paid";
            System.out.println("\nPASSENGER'S TOTAL FARE: "+ totalFarePrice +" Bath");
            System.out.print("ENTER AMOUNT TO PAY: ");
            this.pay = sn.nextDouble();
        }
        if(this.pay < totalFarePrice){
            System.out.println("Invalid Input!");
        }else{
            this.change = this.pay - totalFarePrice;
            System.out.println("CHANGE: " + this.change + " Bath");
            System.out.println("");
            this.status = "paid";
        }
    }
    public double getChange(){
        return this.change;
    }
    public String getStatus(){
        return this.status;
    }
    public double getPay(){
        return this.pay;
    }
}

class View{
    public void search(String name, String destination, double facePrice, int noOfPassenger, String status, double pay, double change, double totalFarePrice){
		System.out.println("***************************************");
		System.out.println("**        PASSENGER'S DETAILS        **");
		System.out.println("***************************************");
		System.out.println("PASSENGER'S NAME: " + name);
		System.out.println("PASSENGER'S DESTINATION : " + destination);
		System.out.println("FARE PRICE: " + facePrice + " Bath");
		System.out.println("NO. OF PASSENGERS: " + noOfPassenger);
		System.out.println("TOTAL FARE PRICE: "+ totalFarePrice +" Bath");
        if(status.equals("paid")){
		    System.out.println("PAY: " + pay + " Bath");
    	    System.out.println("CHANGE: " + change + " Bath");
    	    System.out.println("STATUS: PAID");
        }else{
            System.out.println("STATUS: NOT PAID");
		    System.out.println("***************************************");
		    System.out.println("***************************************");
        }
    }                       
}
class MainMenu{
    public void menu(){
        System.out.println("********************************************");
    	System.out.println("** BUS RESERVATION AND TICKETING SYSTEM   **");
    	System.out.println("********************************************");
    	System.out.println("** [1] Destination                        **");
    	System.out.println("** [2] Enter Passengers And Show Ticket   **");
    	System.out.println("** [3] Billing                            **");
    	System.out.println("** [4] View                               **");
    	System.out.println("** [5] Exit                               **");
    	System.out.println("********************************************");
    	System.out.println("********************************************\n");
    }
    public void menuDestination(int available[]){
        System.out.println("******************************************************************");
		System.out.println("**   DESTINATION             |  FARE(Bath)  |  SEAT  **");
		System.out.println("------------------------------------------------------------------");
		System.out.println("| 1.)Su-ngai Kolok           |  150         |  "+available[1]+"   |");
		System.out.println("| 2.)Narathiwat              |  120         |  "+available[2]+"   |");
		System.out.println("| 3.)Yala                    |  25          |  "+available[3]+"   |");
		System.out.println("| 4.)Naphradu                |  60          |  "+available[4]+"   |");
		System.out.println("| 5.)Chana - Hatyai          |  120         |  "+available[5]+"   |");
		System.out.println("------------------------------------------------------------------");
		System.out.println("******************************************************************\n");
    }
}