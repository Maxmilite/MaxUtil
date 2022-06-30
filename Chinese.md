# MaxUtil
这是一个构建在 Fabric 上的较为合规（指在服务器中）的辅助 MOD.

## 1. 下载

MOD 可以在 [最新发行版本](https://github.com/Maxmilite/MaxUtil/releases/latest) 中下载.

如果你的客户端已经安装了 Fabric，跳到步骤 4.

1. 下载 [Fabric 安装器](https://fabricmc.net/use/) 并且运行.
2. 选择 1.19 和 `.minecraft` 目录，勾选 `create profile / 创建档案`
3. 点击 `Install / 安装` 然后等待程序安装完成.
4. 打开 `.minecraft ` 目录，建立 `mods` 文件夹.
5. 把从 [最新发行版本](https://github.com/Maxmilite/MaxUtil/releases/latest) 下载的 jar 文件拖到这个文件夹里.
6. 启动游戏.

## 2. 使用

在游戏中，首先输入前缀 `:MU`，然后输入以下的某一种指令.

### 1. bind

用一个快捷键开关功能.

模板: `:MU bind <功能> <键位>`

所有功能可以在 `3. 功能` 中找到.

大小写可以忽略.

示例: `:MU bind strafe x`

在 [此处](https://github.com/Maxmilite/MaxUtil/blob/main/src/main/java/io/github/maxmilite/maxutil/util/KeyUtil.java) 查询键位对应表.

如果要取消一个键位，只需在 `<键位>` 处随便输入一个不是键位的单词即可.

示例: `:MU bind strafe oifnaoinfiosanfoiasnfioas`

### 2. toggle (as known as "t")

启用或关闭一个功能.

模板: `:MU toggle <功能> / :MU t <功能>`

所有功能可以在 `3. 功能` 中找到.

大小写可以忽略.

示例: `:MU t nightvision`

### 3. set

设置某一功能的属性

示例: `:MU set <功能> <属性> <值>`

所有功能可以在 `3. 功能` 中找到.

某一功能的所有属性可以在 `3. 功能` 的这一功能后找到.

大小写可以忽略.

示例: 

> `:MU set nightvision mode gamma`
>
> `:MU set nofirerender alpha 0.3` 
>
> `:MU set strafe autojump true  `

## 3. 功能

### 暴力

> 某些服务器可能严格禁止使用这些功能, 如果使用可能会导致被服务器封禁. 使用这些功能需要自担风险!

- `NoSlow`

  > 取消使用某些物品（如吃东西）的时候的减速.

- `Reach`

  > 调整玩家的交互距离.
  >
  > 属性: `reach [距离]`

- `Strafe`

  > 半空中灵活移动.
  >
  > 属性: `amplifier [速率, 1.0 为正常速率], autojump [是否启用自动跳跃]`

### 移动

- `Sprint`

  > 自动疾跑
  >
  > 属性: `alldirections [前后左右均疾跑](若使用可能被封禁)`

### 玩家

- `NightVision`

  >夜视，关闭 1.19 对伽马值的限制
  >
  >属性: `Mode: {GAMMA, EFFECT} [模式] ` 

### 渲染

- `ActiveMods`

  > 在游戏 HUD 中显示已经激活的模块.

- `NoFireRender`

  > 使着火效果透明.
  >
  > 属性: `alpha [透明度 0 ~ 1]`

- `NoInWallOverlay`

  > 关闭卡墙的黑屏.

- `NoPumpkinRender`

  > 关闭南瓜头遮罩.

- `NoScoreboard`

  > 关闭计分板显示.

- `NoUnderwaterOverlay`

  > 尚待补充.

- `Scoreboard`

  > 调整计分板背景色的透明度.
  >
  > 属性: `bgalpha [内容透明度 0 ~ 1], titlealpha [标题栏透明度 0 ~ 1]`
