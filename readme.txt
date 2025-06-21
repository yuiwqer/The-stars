
# 概述

我希望这个项目可以记录我的一个进步.



## Unity框架-Ecs架构-业务逻辑写法

- Ecs包含 `Entity` `Component` `System` 3个部分
1. 我希望`Component`是我的`网络同步组件` 比如`PlayerComponent.cs`,它负责同步一些其他System的成员变量.
2. `System`是我的基本逻辑组件 比如`PlayerMoveSystem.cs` `PlayerJumpSystem.cs` `PlayerInventorySystem.cs` 所有业务和逻辑都要写在System里.
3. 关于`Entity`我暂时没有新的想法`搁置`. Entity负责保存Id 但是好像目前我没有找到它应该存在的地方.
