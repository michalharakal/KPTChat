# Matrix Multiplikation

Eine wichtiger Bestandteil für KI Entwicklung sind Tensoren, und um zu verstehen, was Tensoren sind, ist es wichtig Matrizen zu verstehen.

Daher besteht diese Übung darin, zwei Matrizen miteinander zu multiplizieren.

Als Auffrischung gibt es hier eine Information, wie das geht: [Multiplikation zweier Matrizen](https://statmath.wu.ac.at/~leydold/MOK/HTML/node17.html)

Wir haben bereits einen einfachen `Matrix` Typ eingeführt und eine Methode, um einfache Matrizen zu erzeugen:

```
let M = listOf(1, 2, 3, 4, 5, 6).toMatrix(3)
```

Erzeugt eine Matrix **M** mit drei Zeilen und zwei Spalten:

$$\begin{matrix}
1 & 4
\end{matrix}$$
$$\begin{matrix}
2 & 5
\end{matrix}$$
$$\begin{matrix}
3 & 6
\end{matrix}$$

Die Dimension der Matrix wird von `M.shape()` angegeben.