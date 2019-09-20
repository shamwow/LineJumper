# LineJumper

Jetbrains plugin that enables custom line jump actions (moving text caret X number of lines up or down). Keyboard shortcuts can be assigned to these actions.

## Basic Usage

- After installing, go to the 'Code' menu item and from the 'Line Jumper' pop out, select 'Create Jump Action'.

<p align="center"><img src="https://github.com/shamwow/LineJumper/blob/master/screen.png" alt="Line Jumper Menu" height="400" /></p>

- Enter the number of lines the action should jump and confirm.
- This wil create 'Jump Up X' and 'Jump Down X' actions, shown in the 'Line Jumper' menu pop out. Keyboard shortcuts can then be assigned to each of these.
- Action is configured to select while jumping if shift is held when using with keyboard shortcuts. To configure this, also assign the same keyboard shortcut with shift held down to the jump action. For example, if the key board shortcut to jump up 10 lines is `alt + up-arrow`, also assign `alt + shift + up-arrow` to jump up 10 lines.
- Jump configuration is stored in the Jetbrains IDE config folder as `linejumper.xml`.
- Jump actions can be removed individually via 'Remove Jump Actions' in the 'Line Jumper' pop out.

## License

MIT
