package storage;

import model.Member;
import enumeration.Gender;
import enumeration.MembershipType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final Path MEMBERS_FILE = Paths.get("src/storage/members.csv");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final List<Member> members = new ArrayList<>();
    private static int nextId = 1;

    static {
        loadFromFile();
    }

    // Load data from CSV file
    private static void loadFromFile() {
        members.clear();

        if (!Files.exists(MEMBERS_FILE)) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(MEMBERS_FILE);

            // Skip header row (index 0)
            for (int i = 1; i < lines.size(); i++) {
                String[] columns = lines.get(i).split(","); // -1 keeps empty values

                // Parse each column
                int memberId = Integer.parseInt(columns[0].trim());
                String firstName = columns[1].trim();
                String lastName = columns[2].trim();
                String email = columns[3].trim();
                MembershipType membershipType = MembershipType.valueOf(columns[4].trim());
                LocalDate birthdate = LocalDate.parse(columns[5].trim(), DATE_FORMATTER);
                Gender gender = Gender.valueOf(columns[6].trim());
                String contactNumber = columns[7].trim();
                String address = columns[8].trim().replace(";", ","); // Convert back from CSV-safe format
                LocalDate membershipStartDate = LocalDate.parse(columns[9].trim(), DATE_FORMATTER);
                LocalDate membershipEndDate = LocalDate.parse(columns[10].trim(), DATE_FORMATTER);

                // Create member
                Member member = new Member(
                        firstName, lastName, email, membershipType,
                        birthdate, gender, contactNumber, address,
                        membershipStartDate
                );
                // Set the ID and end date
                member.setMemberId(memberId);
                member.setMembershipEndDate(membershipEndDate);

                members.add(member);

                // Update nextId
                if (memberId >= nextId) {
                    nextId = memberId + 1;
                }
            }

        } catch (IOException e) {
            System.out.println("❌ Error reading members file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error parsing members data: " + e.getMessage());
        }
    }

    // Save data to CSV file
    private static void saveToFile() {
        List<String> lines = new ArrayList<>();

        // Header row
        lines.add("id,firstName,lastName,email,membershipType,birthdate,gender,contactNumber,address,membershipStartDate,membershipEndDate");

        // Data rows
        for (Member member : members) {
            String row = String.join(",",
                    String.valueOf(member.getMemberId()),
                    member.getFirstName(),
                    member.getLastName(),
                    member.getEmailAddress(),
                    member.getMembershipType().name(),
                    member.getBirthdate().format(DATE_FORMATTER),
                    member.getGender().name(),
                    member.getContactNumber(),
                    member.getAddress().replace(",", ";"), // Make address CSV-safe
                    member.getMembershipStartDate().format(DATE_FORMATTER),
                    member.getMembershipEndDate().format(DATE_FORMATTER)
            );
            lines.add(row);
        }

        try {
            Files.write(MEMBERS_FILE, lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            System.out.println("❌ Error saving members file: " + e.getMessage());
        }
    }

    // ========== PUBLIC CRUD METHODS ==========

    // CREATE
    public static int addMember(Member member) {
        member.setMemberId(nextId++);

        // Ensure end date is calculated if not set
        if (member.getMembershipEndDate() == null) {
            member.setMembershipEndDate(
                    member.getMembershipStartDate().plusMonths(member.getMembershipType().getDurationInMonths())
            );
        }

        members.add(member);
        saveToFile();
        return member.getMemberId();
    }

    // READ ALL
    public static List<Member> getAllMembers() {
        return new ArrayList<>(members);
    }

    // READ BY ID
    public static Member getMemberById(int id) {
        return members.stream()
                .filter(m -> m.getMemberId() == id)
                .findFirst()
                .orElse(null);
    }

    // READ BY NAME (search)
    public static List<Member> searchByName(String searchTerm) {
        String term = searchTerm.toLowerCase().trim();
        return members.stream()
                .filter(m -> m.getFirstName().toLowerCase().contains(term) ||
                        m.getLastName().toLowerCase().contains(term) ||
                        m.getFullName().toLowerCase().contains(term))
                .toList();
    }

    // READ ACTIVE MEMBERS
    public static List<Member> getActiveMembers() {
        return members.stream()
                .filter(Member::isActive)
                .toList();
    }

    // READ INACTIVE MEMBERS
    public static List<Member> getInactiveMembers() {
        return members.stream()
                .filter(m -> !m.isActive())
                .toList();
    }

    // UPDATE
    public static void updateMember(Member updatedMember) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getMemberId() == updatedMember.getMemberId()) {
                members.set(i, updatedMember);
                saveToFile();
                return;
            }
        }
    }

    // DELETE
    public static void deleteMember(int id) {
        boolean removed = members.removeIf(m -> m.getMemberId() == id);
        if (removed) {
            saveToFile();
        }
    }

    public static boolean hasMembers() {
        return !members.isEmpty();
    }
}