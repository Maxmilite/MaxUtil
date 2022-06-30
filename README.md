# MaxUtil
[View Chinese tutorial here](https://github.com/Maxmilite/MaxUtil/blob/main/Chinese.md) 

A legit utility mod on fabric.

## 1. Download

You can download this mod from [Latest Release](https://github.com/Maxmilite/MaxUtil/releases/latest).

If you already have Fabric Loader installed, skip to step 4.

1. Download the [Fabric installer](https://fabricmc.net/use/) and run it.
2. Make sure 1.19 is selected, the path to your `.minecraft` folder is correct, and `create profile` is checked.
3. Click `Install` and then wait for the process to complete.
4. Open your `.minecraft` folder and create a folder within it called `mods`.
5. Drag the downloaded jar file from [Latest Release](https://github.com/Maxmilite/MaxUtil/releases/latest) into the mods folder.
6. Open the Minecraft Launcher and select the Fabric 1.19 profile.
7. Launch your game and enjoy it :) .

## 2. Usage

When in game, input the prefix `:MU ` in your chatbot initially, and then input one of the methods below.

### 1. bind

Bind a key to toggle a module.

Template: `:MU bind <Module> <Key>`

All the modules in this mods can be found in `3. Module`.

The case can be ignored.

Example: `:MU bind strafe x`

View the keymap [here](https://github.com/Maxmilite/MaxUtil/blob/main/src/main/java/io/github/maxmilite/maxutil/util/KeyUtil.java).

### 2. toggle (as known as "t")

Toggle a module.

Template: `:MU toggle <Module> / :MU t <Module>`

All the modules in this mods can be found in `3. Module`.

The case can be ignored.

Example: `:MU t nightvision`

### 3. set

Set the attribute of specific module.

Template: `:MU set <Module> <Attribute> <Value>`

All the modules in this mods can be found in `3. Module`.

All the attributes of a module can be found after this module in `3. Module`.

The case can be ignored.

Example: 

> `:MU set nightvision mode gamma`
>
> `:MU set nofirerender alpha 0.3` 
>
> `:MU set strafe autojump true  `

## 3. Module

### Blatant

> Using these modules maybe strictly prohibited in some servers, which can cause a ban from the server. Use them at your own risk!

- `NoSlow`

  > Cancel slowdown when using some items.

- `Reach`

  > Adjust the reach of player.
  >
  > Attributes: `reach`

- `Strafe`

  > Enable player to move freely in mid-air.
  >
  > Attributes: `amplifier, autojump`

### Movement

- `Sprint`

  > Auto sprint
  >
  > Attributes: `alldirections (blatant)`

### Player

- `NightVision`

  >Break the restrictions on illegal gamma value of Minecraft 1.19.
  >
  >Attributes: `Mode: {GAMMA, EFFECT}`

### Render

- `ActiveMods`

  > Show active mods in game hud.

- `NoFireRender`

  > Make fire overlay translucent.
  >
  > Attributes: `alpha`

- `NoInWallOverlay`

  > Disable overlay in wall.

- `NoPumpkinRender`

  > Disable overlay when wearing a pumpkin.

- `NoScoreboard`

  > Disable the display of scoreboard.

- `NoUnderwaterOverlay`

  > No use currently.

- `Scoreboard`

  > Adjust the opacity of the background of scoreboard.
  >
  > Attributes: `bgalpha, titlealpha`