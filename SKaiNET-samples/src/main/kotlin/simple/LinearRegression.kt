package de.jugda.simple

/**
import torch
import torch.nn as nn
import torch.optim as optim

X = torch.tensor([[1.0], [2.0], [3.0], [4.0]], dtype=torch.float32)
y = torch.tensor([[2.0], [4.0], [6.0], [8.0]], dtype=torch.float32)

# Define a linear regression model and its forward pass
class LinearRegression(nn.Module):
    def __init__(self):
        super(LinearRegression, self).__init__()
        self.linear = nn.Linear(1, 1)  # 1 input feature, 1 output feature

    def forward(self, x):
        return self.linear(x)

# Instantiate the model
model = LinearRegression()

# Inspect the model's state dictionary
print(model.state_dict())

# Define loss function and optimizer
criterion = nn.MSELoss()
# setting our learning rate "hyperparameter" here
optimizer = optim.SGD(model.parameters(), lr=0.01)

# Training loop that includes forward and backward pass
num_epochs = 100
for epoch in range(num_epochs):
    # Forward pass
    outputs = model(X)
    loss = criterion(outputs, y)
    RMSE_loss  = torch.sqrt(loss)

    # Backward pass and optimization
    optimizer.zero_grad()  # Zero out gradients
    RMSE_loss.backward()  # Compute gradients
    optimizer.step()  # Update weights

    # Print progress
    if (epoch+1) % 10 == 0:
        print(f'Epoch [{epoch+1}/{num_epochs}], Loss: {loss.item():.4f}')

# After training, let's test the model
test_input = torch.tensor([[5.0]], dtype=torch.float32)
predicted_output = model(test_input)
print(f'Prediction for input {test_input.item()}: {predicted_output.item()}')
*/