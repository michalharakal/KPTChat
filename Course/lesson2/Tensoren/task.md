# Tensoren

## Motivation

Nach dem Encodieren der Daten werden sie in ein Trainings- und ein Testset aufgeteilt. Hierbei taucht der Begriff "Tensor" auf:

```
# Train and test splits
data = torch.tensor(encode(text), dtype=torch.long)
n = int(0.9*len(data)) # first 90% will be train, rest val
train_data = data[:n]
val_data = data[n:]
```
![TrainTestSplit](https://www.plantuml.com/plantuml/png/JOmn3i8m34NtdCBg1PXOEg0oCbDrjne35gaJsMubbfuTDQgWszDxNxypH_LvIg7xKQA7WsTGVhuS8YkDpbc2lLaAAauWEP9HWNC86q9p4x-gugS1D60fi--XLsIfrZVQxO298phLi60wZayzcSEz9FQQqKAeZx2kbqsqCD3eMGCQL5UVeGKdiODUCFrbHnBdANq1)

## Was ist ein Tensor

Allgemein: "Tensoren sind Größen, mit deren Hilfe man Skalare, Vektoren sowie weitere Größen analoger Struktur in ein einheitliches Schema einordnen kann, um mathematische und physikalische Zusammenhänge zu beschreiben."

So wie ein Vektor mit nur einem Element ein Skalar ist und eine Matrix mit einer Spalte ein Vector, ist eine Matrix ein Tensor in der Dimension 2 ⨯ 2.
Daher können wir auch sagen, dass ein Tensor 0-ter Stufe ein Skalar, ein Tensor 1. Stufe ein Vektor, und ein Tensor 2. Stufe eine Matrix ist.
Also sind Skalare, Vektoren und Matrizen einfach nur Formen von Tensoren.
Tensoren darüber hinaus aber in beliebigen Stufen definiert, es gibt also Tensoren n-ter Stufe, die noch mehr Dimensionen haben.

## Und was hat das mit dem Code zu tun?

Tensoren erlauben uns unter anderem, Algorithmen und mathematische Zusammenhänge zu vereinfachen. So wie Rechenoperationen für Zahlen, Vektoren und Matrizen definiert sind, sind sie es auch für Tensoren n-ter Stufe. So ist es dann unerheblich, ob ein Input 2, 3 oder 4 Dimensionen hat.

Tensoren sind also harmlos, einen dreidimensionalen Tensor können wir uns als Stapel von Matrizen vorstellen. Mit Tensoren kann ganz normal gerechnet werden, und es ist einfach nur ein umfassender Begriff für eine mathematische Sturktur