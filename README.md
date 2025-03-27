# Running the Program
1) Navigate to the directory: `chillteam7-game-catalog`
2) Open up command prompt and ensure java and maven are installed by running: `java & maven`
4) Run the program: `mvn compile exec:java -Dexec.mainClass="Main"`

# Testing (4CB 3TB 3OB)
| Test ID  | Method/Class                  | Inputs(s)                                                                 | Expected Output(s)           | Testing Approach | Assigned Team Member |
|----------|-------------------------------|--------------------------------------------------------------------------|------------------------------|------------------|----------------------|
| UT-01-CB | Card (Constructor and Getters)| "Zelda", {"Adventure"}, "1", "A legend of Zelda game", "zelda.jpg"        | Getters return correct values| Automated (Junit)| Alexis               |
| UT-02-CB | Card (Constructor and Getters)| null values for all fields                                                | Getters return null          | Automated (Junit)| Alexis               |
| UT-03-CB | Card (Constructor and Getters)| "", [""], "", "", ""                                                      | Getters return empty values  | Automated (Junit)| Alexis               |
| UT-04-CB | Card.toString()               | Card("Zelda", {"Adventure"}, "1", "A legend of Zelda game", "zelda.jpg")  | String contains correct values| Automated (Junit)| Alexis               |

| Test ID   | Components Involved               | Preconditions                        | Steps                         | Expected Result                         | Assigned Team Member |
|-----------|-----------------------------------|--------------------------------------|-------------------------------|-----------------------------------------|----------------------|
| IT-01-TB  | catalogueUI & Card & Database     | Database has data or mock data.      | Run main                      | Cards appear in the catalogueUI         | Renuston             |
| IT-02-TB  | catalogueUI                       | Database                             | Run main                      | Cards get                               | Manu                 |
| IT-03-TB  | Card & SortGame & Database        | Database has data or mock data.      | Run main and apply sorting    | Cards sorted in correct order in catalogueUI |                      |
| IT-04-TB  | catalogueUI & Card & SearchGames & Database | Database has data or mock data.      | Run main then search for a game | Cards with correct name will appear in catalogueUI | Tony                 |

| Test ID  | Scenario            | Preconditions       | Steps                           | Expected outcome                                | Assigned Team Member |
|----------|---------------------|---------------------|---------------------------------|------------------------------------------------|----------------------|
| ST-01-OB | Sorting for games   | Program is running  | Apply sorting of choice         | UI should show to the user games in correct order | Alexis               |
| ST-02-OB | Searching for games | Program is running  | Search for a game and apply search | UI should show to the user games with that name  | Alexis               |
| ST-03-OB | Adding a game       | Program is running  | Click add game button           | UI should show the newly added game             | Alexis               |

---
# Burndown Chart as of Finishing Iteration 2
![IMG_2737](https://github.com/user-attachments/assets/4d1141c0-d2de-4a02-923d-bab3967c4f6a)
<br> Here is our progess as of iteration 2.

# CatalogueUI Iteration 2 Chill Team 7 StateChart Diagram
![Screenshot 2025-03-08 135324](https://github.com/user-attachments/assets/b1ca80a4-e52f-4bc9-b8c4-96252a976937)
 <br> Here is our StateChart diagram for catalogueUI as of iteration 1.

# UML Iteration 1 Chill Team 7 Diagram

![UMLChillTeam7 (1)](https://github.com/user-attachments/assets/aa7a0af4-79ad-4de0-8e94-296c68bec234)
 <br> Here is our UML diagram based on our requirements from our phase 1 planning and finalizations.

# UML Sequence Diagram Chill Team 7 Diagram
![ChillTeam7umlSequence](https://github.com/user-attachments/assets/725c0f66-b299-4c4e-9701-d28a525aaaf7)
  <br> Here is our UML sequence diagram for adding a game.
# Coding Practices
## Comments
1) Descriptive comments for functions, classes, etc. (if it isn't intuitive)
```java
// Adds two numbers
public void addNumsFunction(int num1, int num2){}
```
2) Comments for code developed by others. Please prepend your name
```java
// Aidan: What is this for?
System.out.println(var1);
```
## Classes and Objects
If something can be a class, and should be a class, implement it as a class!

## Master Branch V.S. Other Branches
- If you want to implement simple fixes, or add commments, commit and push to main
- If you are working on a large feature, such as a user story, push to another branch

# Simplified Project Structure
```
game-catalog/
├── src/
│   ├── backend/
│   │   ├── Database.java
│   │   ├── other-files.java
│   ├── frontend/
│   │   ├── catalogUI.java
│   │   ├── other-files.java
│   ├── main.java
```
# Other
- Frontend leads and backend leads have the final say in their respective areas
- If you get merge conflicts, run `git mergetool`
- Commit often!
