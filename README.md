### Darwin World Simulation
- Run simulation by `java Darwin`
- Help by `java Darwin -h`
- Game board configuration are included in `configs/`
    - Example config 
    ```
    WORLD_XY 20 20

    SPECIES_FILE creatures/Minion.txt
    NUM_CREATURES 8
    PLACEMENT_MODE RANDOM

    SPECIES_FILE creatures/Rover.txt
    NUM_CREATURES 8
    PLACEMENT_MODE RANDOM

    SPECIES_FILE creatures/Landmine.txt
    NUM_CREATURES 8
    PLACEMENT_MODE RANDOM

    SPECIES_FILE creatures/Wolf.txt
    NUM_CREATURES 8
    PLACEMENT_MODE RANDOM
    RUNSTEPS 1000
    ```
- write and test your creatures by adding it to `creatures/`
- Rules for creature's behavior is included in `documentation/`, soon would be updated here