# Floor Planner
My personal project for this semester is a tool for 
constructing architectural floor plans. This tool should allow
users to create projects of arbitrary size and floor count.
Users should be able to construct a floor plan for any structure
they can invent given that said structure only involves rectangular 
rooms and furniture with rectangular bounds. Floors will contain
connected rooms which will contain furniture. Users should not be
able to place furniture on top of other furniture and also be 
able to enforce minimum distances between pieces of furniture
as well as wall sizes. This project will be used by interior 
designers and architects. This project is personally interesting 
to me as I've always enjoyed redesigning the interior of the 
spaces I've lived in. Having a tool to change things around on 
the fly and try out new configurations would be quite useful.

Though interior designers may be interested in using this application, I 
intend to prioritize the needs of architects during my design.
This means that I intend to design a function to render, 
for completed projects, a floor plan that an architect 
would be able to use to their specifications.

_I will be researching exactly what this will look like when I
reach that stage of the project_

## User Stories
For architects:
- As a user, I want to be able to add a floor
- As a user, I want to be able to add a room to a floor
- As a user, I want to be able to modify the connections between rooms
- As a user, I want to be able to modify the minimum margins between furniture
- As a user, I want to be able to be able to modify the lot dimensions
  (maximum floor size)
- As a user, I want to be able to print out a floor plan which shows 
  the dimensions of each room and floor formatted for approval from
  builders
- As a user, I want to be able to load a plan from a file
- As a user, I want to have an option to save my plan when quitting to the main menu

For interior designers:
- As a user, I want to be able to design coloured rectangular furniture
- As a user, I want to be able to add furniture to a room
- As a user, I want to be able to change the paint colour of a room
- As a user, I want real time feedback while dragging furniture about 
  whether I can place it in a given location – it should not be possible to 
  place furniture on top of other furniture or within the margin of other
  furniture

## Instructions for Grader
- To add multiple floors to a plan, 
1. Choose a plan from the main menu (I suggest you use the Musqueam plan)
2. Now an empty architecture planner the size of the lot specified in Musqueam Plan should appear accompanied by a 
   toolbar which displays the floors as well as options for making, selecting, and deleting floors.
3. To add a floor to the plan, click the "Make Floor" button on the toolbar
4. Draw a rectangular floor on the lot by clicking and dragging on the planner
5. After letting go, you should be prompted for a label for the floor and its number
6. Now the floor can be selected from the list in the toolbar (click on it in the list and then click select 
   and it will be displayed along with its number and label

- To delete a floor,
1. Complete steps 1 and 2 from the previous procedure
2. Choose a floor by clicking it
3. Select delete, and the floor will be deleted

- You have already seen the visual component of my application – the display of the floors

- You can save the state of my application by selecting "Quit" on the toolbar and choosing to overwrite the existing
  file
- You can reload the state of my application by selecting a saved plan from the main menu

## Phase 4: Task 2
Sample event log:
```
Fri Dec 02 03:19:27 PST 2022
Event log cleared.
Fri Dec 02 03:19:37 PST 2022
Floor (1): Lobby added to Musqueam Project
Fri Dec 02 03:19:43 PST 2022
Floor (2): Second Floor added to Musqueam Project
Fri Dec 02 03:19:50 PST 2022
Floor (-1): P1 added to Musqueam Project
Fri Dec 02 03:19:56 PST 2022
Floor (2): P2 added to Musqueam Project
Fri Dec 02 03:20:00 PST 2022
Floor (2): P2 removed from Musqueam Project
Fri Dec 02 03:20:06 PST 2022
Floor (-1): P1 removed from Musqueam Project
```

## Phase 4: Task 3
Given time to refactor my code, I would implement the Composite
design pattern. I have a needlessly complex hierarchy of 
objects that can go into a plan that would be far better served
with a composite design. This would remove a lot of duplicated
code and allow for the easy addition of new features such as
additional plan object types with relative ease. Additionally,
it could repair the potentially needless and confusing
distinction between a plan (which contains floors and is 
not a plan object) and plan object such as a floor or a room. A
revised design might replace PlanObject and Plan with two 
abstract classes: Container and Standalone, where containers could hold
other containers and standalones while standalones would be
standalone plan objects. Both of these would extend a Component 
class, allowing for easy iteration and interaction with common
features.