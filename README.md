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
- As a user, I want to be able to be able to change the lot dimensions
  (maximum floor size)
- As a user, I want to be able to print out a floor plan which shows 
  the dimensions of each room and floor formatted for approval from
  builders

For interior designers:
- As a user, I want to be able to design coloured rectangular furniture
- As a user, I want to be able to add furniture to a room
- As a user, I want to be able to change the paint colour of a room
- As a user, I want real time feedback while dragging furniture about 
  whether I can place it in a given location – it should not be possible to 
  place furniture on top of other furniture or within the margin of other
  furniture