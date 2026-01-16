package Service;

import Enumeration.Gender;
import Enumeration.MembershipType;
import Model.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GymService extends BaseGymService{

    // Test members
    public GymService(){
        memberList.add(new Member("Kevin", "Flores", MembershipType.BASIC,
                LocalDate.of(1995, 6, 26), Gender.MALE, "09157684654",
                "258 Talumpong Street"));
        memberList.add(new Member("Gi-ann Camille", "Huit", MembershipType.VIP,
                LocalDate.of(1998, 1, 7), Gender.FEMALE, "09157684654",
                "258 Talumpong Street", LocalDate.now().minusMonths(14)));
        memberList.add(new Member("Kianna-louise", "Flores", MembershipType.PREMIUM,
                LocalDate.of(2024, 2, 8), Gender.FEMALE, "09157684654",
                "258 Talumpong Street"));
        memberList.add(new Member("John", "Doe", MembershipType.PREMIUM,
                LocalDate.of(1985, 5, 10),
                Gender.MALE, "09150000000", "123 Test Street",
                LocalDate.now().minusMonths(14)
        ));
    }

    @Override
    public void registerMember(Scanner scan){

        try{
            System.out.print("Enter first name: ");
            String firstName = scan.nextLine();
            if (firstName.isEmpty()){
                throw new IllegalArgumentException("First name cannot be empty.");
            }

            System.out.print("Enter last name: ");
            String lastName = scan.nextLine();
            if(lastName.isEmpty()){
                throw new IllegalArgumentException("Second name cannot be empty.");
            }

            System.out.print("Enter membership type (0 - Basic, 1 - Premium, 2 - VIP): ");
            int memberType = Integer.parseInt(scan.nextLine());
            if (memberType < 0 || memberType > 2){
                throw new IllegalArgumentException("Invalid membership type");
            }
            MembershipType membershipType = MembershipType.values()[memberType];

            System.out.print("Enter birthdate: ");
            LocalDate birthDate = null;

            try{
                birthDate = LocalDate.parse(scan.nextLine().trim());
            } catch (IllegalArgumentException e){
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }

            System.out.print("Enter gender (0 - MALE, 1 - FEMALE): ");
            int gender = Integer.parseInt(scan.nextLine());
            Gender genderEnum = Gender.values()[gender];

            System.out.print("Enter contact number: ");
            String contactNumber = scan.nextLine();

            System.out.print("Enter address: ");
            String address = scan.nextLine();

            memberList.add(new Member(firstName, lastName, membershipType, birthDate, genderEnum, contactNumber, address));
        }catch (NumberFormatException e){
            System.out.println("Invalid input. Please enter a valid number.\n");
        }catch (IllegalArgumentException e){
            System.out.println("Invalid input.\n");
        }
        catch (Exception e){
            System.out.println("Registration Failed.\n");
        }

    }

    @Override
    public void renewMembership(){
    }

    @Override
    public void updateMemberInformation(){
    }

    @Override
    public void deleteMember(){
    }

    @Override
    public void memberLookup(){
        System.out.println("Showing active member list…");
        List<String> personNames = memberList.stream().map(x -> x.getFirstName().toUpperCase() + " " +
                x.getLastName().toUpperCase() + " - " + x.getGender() + " - " + x.getBirthdate()).toList();

        personNames.forEach(System.out::println);
        System.out.println("End of list.\n");
    }

    @Override
    public Member memberLookupById(int id){
        return memberList.stream().filter(x -> x.getMemberId() == id).findFirst().orElse(null);
    }

}
