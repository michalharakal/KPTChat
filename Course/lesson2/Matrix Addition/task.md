# Matrix Addition

Ein wichtiger Bestandteil für KI Entwicklung sind Tensoren, und um zu verstehen, was Tensoren sind, ist es wichtig Matrizen zu verstehen.

Wir haben bereits einen einfachen `Matrix` Typ erstellt. Für diesen Kurs ist es einfach eine List mit Lists von Double: `typealias Matrix = List<List<Double>>`

Es gibt auch schon eine Methode, um einfache Matrizen zu erzeugen:

```
val M = listOf(1, 2, 3, 4, 5, 6).toMatrix(3)
```

Das erzeugt eine Matrix **M** mit zwei Reihen und drei Spalten:

```
⎛ 1.0  2.0 ⎞
⎜ 3.0  4.0 ⎟
⎝ 5.0  6.0 ⎠
```

Die Dimension der Matrix wird von `M.shape()` angegeben: `[3, 2]`

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