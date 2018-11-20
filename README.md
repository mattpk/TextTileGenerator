This procedural text tile generator takes a text "tile", and extrapolates it to a larger image using local similarity. Every N*N square in the output must appear in the input.

This project was inspired by the readme found in https://github.com/mxgmn/WaveFunctionCollapse.

Examples:
```
Input:
3
ABA
BAB
ABA

Output:
ABABABABABAB
BABABABABABA
ABABABABABAB
BABABABABABA
ABABABABABAB
BABABABABABA
ABABABABABAB
BABABABABABA
ABABABABABAB
BABABABABABA
ABABABABABAB
BABABABABABA
```
```
Input:
4
⬜⬛⬛⬛
⬜⬛⬛⬛
⬜⬜⬛⬛
⬜⬜⬜⬜

Output:
⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛
⬜⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛
⬜⬜⬜⬜⬛⬛⬛⬛⬛⬛⬛⬛
⬜⬜⬜⬜⬜⬜⬛⬛⬛⬛⬛⬛
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛⬛⬛
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛⬛⬛
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛⬛⬛
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
```
```
Input:
5
_|___
_|___
_|___
___|_
___|_

Output:
______|_____
_|____|_____
_|____|_____
_|_|________
_|_|_____|__
_|__________
_|________|_
__________|_
____|_______
____|_______
__|_________
__|____|____

```
```
Input:
5
~~~~~
~~$#~
~~#$~
~~~~~
~~~~~

Output:
~~~$#~~~~~~~
~~~#$~~~$#~~
~~~~~~~~#$~~
~~~~~~~~~~~$
$#~~$#~~~~~#
#$~~#$~~~~~~
~~~~~~~~~~$#
~~~~~~$#~~#$
$#~~~~#$~~~~
#$~~~~~~~~~~
~~~~~~~~~~~~
~$#~$#~~~~~~
```
