# RenewableShower
RenewableShower is a project aiming to maximise 
the efficiency and eco-friendliness of energy consumption by using a
traffic light system to show when enough renewable energy is in the network

### Why?
Sadly, the time of the day when most of the renewable energies are
produced is also the time when the least energy is consumed so a lot of
it is sold to other countries whilst at night, when less renewable energy
is produced, energy is bought from other countries. This is of course a
complete waste of renewable energy and that's what this project aims to fix
by showing the user when they should use energy and when not.

### How?
The project gets its data from the website [SMARD.de](https://smard.de) 
by the German Bundesnetzagentur and processes that to get the percentage
of renewable energies in the grid which is then uploaded to a Firebase
where a [Frontend Client](https://github.com/Linus-Cyber/EnergieAmpel)
displays it as a traffic light

### Future plans
In the future we want to continue the project by creating hardware,
that you can put in your room to directly see our recommendations
as well as doing some data analysis to analyze the last month to
dynamically adjust the percentage needed for the traffic light to
show green (currently fixed at 40%)