<div align="center">
  <h1>Zaiko</h1>
  <p>Zaiko is a modern and flexible GUI library for Spigot.</p>
    <a href="https://repo.saki.gg/#/releases/gg/saki/zaiko">
      <img src="https://repo.saki.gg/api/badge/latest/releases/gg/saki/zaiko?color=EE83DA&name=zaiko&prefix=v" alt="zaiko release badge"/>
    </a>
    <a href="https://repo.saki.gg/#/releases/gg/saki/zaiko-adventure">
      <img src="https://repo.saki.gg/api/badge/latest/releases/gg/saki/zaiko-adventure?color=EE83DA&name=zaiko-adventure&prefix=v" alt="zaiko-adventure release badge"/>
    </a>

</div>


## Features
- Lots of customization options (including making Templates, custom Placeables, Populators, and more)
- Pre-made Placeables (icons, buttons, toggles, and inputs)
- Menu pagination
- Multi-version supported (1.8.8-1.20+)
- Multiple types of menus (different chest sizes, box, hopper inventories, etc.)
- Relatively small file size (~42KB)
- With more coming soon!


## Installation
### Requirements
- Java 8+ (the [adventure module](./adventure) requires Java 17)
- Spigot 1.8.8+ (the [adventure module](./adventure) requires Paper 1.16+)

1. Add [our releases repository](https://repo.saki.gg/#/releases) to your `build.gradle` or `pom.xml`:
     ```groovy
     maven { name = 'sakiReleases', url 'https://repo.saki.gg/releases/' }
     ```

    ```xml
    <repository>
     <id>saki-releases</id>
     <name>SakiPowered Repository</name>
     <url>https://repo.saki.gg/releases/</url>
   </repository>
    ```

2. Add the zaiko dependency to your `build.gradle` or `pom.xml`:
    ```groovy
    implementation('gg.saki:zaiko:VERSION')
    ```

    ```xml
    <dependency>
     <groupId>gg.saki</groupId>
     <artifactId>zaiko</artifactId>
     <version>VERSION</version>
   </dependency>
    ```
3. *(optional, but recommended)* Relocate the package to avoid shading conflicts ([guide for maven here](https://maven.apache.org/plugins/maven-shade-plugin/examples/class-relocation.html), [guide for gradle here](https://imperceptiblethoughts.com/shadow/configuration/relocation/))

## Usage
[Click here](https://repo.saki.gg/javadoc/releases/gg/saki/zaiko/latest) to view the JavaDocs.
<br/>
<br/>
There's code examples below, and in the [example](./example) and [example-adventure](./example-adventure) modules.
<br/>
<details>
  <summary>Code examples</summary>

### Setting up a menu

Main Plugin Class
```java
public class ExamplePlugin extends JavaPlugin {
    
    private Zaiko zaiko;
    
    @Override
    public void onEnable() {
        this.zaiko = new Zaiko(this);
    }
    
    @Override
    public void onDisable() {
      if (this.zaiko != null) {
        this.zaiko.cleanup();
        this.zaiko = null;
      }
    }
}
```

Menu Class
```java
public class ExampleMenu extends Menu {

  // Setup all non-player dependant menu content
  public ExampleMenu(@NotNull Zaiko zaiko) {
    // Provide the Zaiko instance, title and size
    super(zaiko, "Example Menu", 3 * 9);

    // Assign templates (optional)
    Placeable pane = new Icon(new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).name("").build());
    Placeable pane2 = new Icon(new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).name("").build());

    // Template to fill the outer border with alternating panes
    this.addTemplate(OuterFill.alternating(pane, pane2, OuterFill.ALL));

    // Assigns menu settings (optional)
    this.settings()
      .playerInventoryInteraction(true)
      .transferItems(false)
      .closeable(false);
  }

  @Override
  public void build() {

    // Assign all placeables which should be displayed in the menu, player-dependant.
    Button button = Button.builder().item(new ItemBuilder(Material.CAKE).name("Button").build())
      .action((p, event) -> p.sendMessage("You clicked the button!"))
      .build();

    // Place each placeable instance using either the slot
    this.place(10, button);
    // or with x and y coordinates (starting from 0,0 at the top left).
    this.place(1, 1, button);
  }
}
```

Opening A Menu
```java
Player player;

Menu menu = new ExampleMenu(javaPlugin);
menu.open(player);
```

Paginated Menus
```java
public class ExampleMenu extends Menu { 
    
  private final @NotNull PaginatedPopulator<PotionEffect> populator;  
    
  // Setup all non-player dependant menu content
  public ExampleMenu(@NotNull Zaiko zaiko) {
    // Provide the Zaiko instance, title and size
    super(zaiko, "Example Menu", 3 * 9);

    // Handles pages and the data to be used, aswell as the placeable mapping
    this.populator = new PaginatedPopulator<>(1, 7, 7, player.getActivePotionEffects(), potionEffect -> {
       ItemStack item = new ItemBuilder(Material.POTION).name(potionEffect.getType().getName()).build();

       return new Icon(item);
    });
  }

  @Override
  public void build() {
    this.setTitle("Page " + (this.populator.getCurrentPage() + 1));

    if (this.populator.isEmpty()) {
       this.place(4, new Icon(new ItemBuilder(Material.BARRIER).name("No potion effects").build()));
       return;
    }

    // if we're on the first page, don't show the previous button
    if (!this.populator.isFirstPage()) {
       this.place(0, Button.builder()
               .item(new ItemBuilder(Material.ARROW)
                       .name("Previous page").build())
               .action((p, e) -> this.populator.changePage(this, p, -1)).build());
    }

    // if we're on the last page, don't show the next button
    if (!this.populator.isLastPage()) {
       this.place(8, Button.builder()
               .item(new ItemBuilder(Material.ARROW)
                       .name("Next page").build())
               .action((p, e) -> this.populator.changePage(this, p, 1)).build());        }

    // lastly, populate the menu
    this.populator.populate(this);
  }
}
```

## Placeable Usage

### Icons
Icons are used as blank items which have no additional functionality, and simply serves as a visual element in the container.

```java
Icon icon = Icon.builder()
  .item(new ItemStack(Material.WOODEN_PICKAXE))
  .removable(false) // Defaults false
  .draggable(false); // Defaults false

this.place(slot);
```

### Buttons
Buttons are used as clickable items which trigger an action when any click is detected.

```java
Button button = Button.builder()
  .item(new ItemStack(Material.CAKE))
  .action((player, event) -> player.sendMessage("Player clicker is provided"))
  .build();

this.place(slot);
```

### Inputs
Inputs are used to accept items from player inventories, and receive a response based on the item provided.

```java
Input input = Input.builder()
  // This is the default input item
  .item(new ItemStack(Material.WOODEN_SWORD))
  .action(item -> player.sendMessage("Collected " + itemStack.getType().name()))
  .build();

this.place(slot);
```

### Toggles
Toggles are used to toggle between two states, true and false. Based on the state change, a response will be provided.

```java
Toggle toggle = Toggle.builder()
  // State change logic and item is handled here
  .onChange((toggled, state) -> {
    player.setAllowFlight(state);

    return new ItemBuilder(state ? Material.EMERALD : Material.REDSTONE)
      .name(state ? ChatColor.GREEN + "Flight" : ChatColor.RED + "Flight")
      .lore("State: " + state, ChatColor.AQUA + "Click to toggle your flight").build();
  })
  // This is the default state for the toggle
  .state(player.getAllowFlight()).build();

this.place(slot);
```

</details> 


## Planned Features
- [ ] Animation/menu updater
- [ ] Refactor placeables?
- [ ] Better ItemBuilder
- [ ] More docs/examples
- [ ] Placeable masks

*Have an issue or suggestion? Please make an [issue here](https://github.com/SakiPowered/zaiko/issues/new)*


## Compiling
*Java 17+ is required for compiling*

- Run `./gradlew build` to build all modules.
- Run `./gradlew :example:shadowJar` to build the example plugin.


## License
Zaiko is licensed under the [MIT](./LICENSE) license.
