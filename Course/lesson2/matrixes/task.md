# Matrix Multiplikation

Eine wichtiger Bestandteil für KI Entwicklung sind Tensoren, und um zu verstehen, was Tensoren sind, ist es wichtig Matrizen zu verstehen.

Wir haben bereits einen einfachen `Matrix` Typ erstellt und eine Methode, um einfache Matrizen zu erzeugen:

```
val M = listOf(1, 2, 3, 4, 5, 6).toMatrix(3)
```

Erzeugt eine Matrix **M** mit zwei Reihen und drei Spalten:

$$
\begin{matrix}
1 & 2 & 3
\end{matrix}$$
$$\begin{matrix}
4 & 5 & 6
\end{matrix}
$$

Die Dimension der Matrix wird von `M.shape()` angegeben.

Es gibt auch schon eine Hilfsfunction, mit der die Matrix einigermassen mathemathisch ausgegeben wird: `M.print()`:

```
M.print()

⎛ 1.0  2.0  3.0 ⎞
⎝ 4.0  5.0  6.0 ⎠

```

Die erste Aufgabe ist, zwei Matrizen zu addieren. Dabei ist es notwendig, dass die Matrizen dieselbe Dimension haben. Dann werden sie praktisch "übereinander gelegt", und addiert:  

```
⎛ 1  2 ⎞ + ⎛ 5  6 ⎞ 
⎝ 3  4 ⎠   ⎝ 7  8 ⎠

= ⎛ 1 + 5  2 + 6 ⎞ = ⎛  6   8 ⎞
  ⎝ 3 + 7  4 + 8 ⎠   ⎝ 10  12 ⎠
```

Daher besteht diese Übung darin, zwei Matrizen miteinander zu multiplizieren.

Als Auffrischung gibt es hier eine Information, wie das geht: [Multiplikation zweier Matrizen](https://statmath.wu.ac.at/~leydold/MOK/HTML/node17.html)

