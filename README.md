<p align="center">
  <img src="images/logo.png">
</p>

<br>

# AutoSmell

<p>Add automatic smell and no cobblestone feature to your Minecraft server</p>
<p>Tested minecraft versions: </p> 

`1.19.3`

# Installation

<p>Put AutoSmell.jar to your plugins folder and restart the server.</p>

# Configuration

`Use § sign instead of & for colors`

<details><summary>config.yml</summary>

`prefix` - Commands prefix<br>
`no-permission` - No permission message, preceded by prefix<br>
`setAutoSmell` - Set autosmell message (/autosmell, /piec)<br>
`setCobblestone` - Set no cobblestone message (/cobblestone, /bruk)<br>
`setOn` - "You set autosmell to <on>"<br>
`setOff` - "You set autosmell to <off>"<br>
`autoSmellPermission` - Permission to use /autosmell<br>
`noCobblestonePermission` - Permission to use no cobblestone feature (/cobblestone)<br>
`managePermission` - Permission to manage plugin (/autosmell reload, /cobblestone reload)<br>
`cobblestoneBlocks` - You can disable not only cobblestone. This is list of blocks which will have disabled drops when you have no cobblestone feature turned on<br>

## Default configuration:

```yml
config:
  prefix: "§2[§eAS§2] "
  no-permission: "§cYou don't have permission!"
  setAutoSmell: "§aYou set autosmell to"
  setCobblestone: "§aYou set no cobblestone to"
  setOn: "§aOn"
  setOff: "§cOff"
  autoSmellPermission: "autosmell.use.smell"
  noCobblestonePermission: "autosmell.use.cobblestone"
  managePermission: "autosmell.manage"
  cobblestoneBlocks:
    - TUFF
    - COBBLESTONE
    - STONE
    - DEEPSLATE
    - NETHERRACK
    - BLACKSTONE
    - COBBLED_DEEPSLATE
    - ANDESITE
    - DIORITE
    - GRANITE
```

</details>

# Commands & Permissions

`/autosmell` - Switch autosmelling ores (gold/iron/copper ore, ancient debris). You need to set permission in config.<br>
`/piec` - Alias for /autosmell<br>
`/cobblestone` - Switch no cobblestone feature (when on disables drops from blocks listed in config). You need to set permission in config.<br>
`/bruk` - Alias for /cobblestone<br>
`/autosmell reload or /cobblestone reload` - Reload configuration. You need to set permission in config. You need to set permission in config.<br>
