<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Make Payment</title>
    <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Select Your Class & Pay</h2>
    <form id="payment-form">
        <label>Select Class:</label>
        <select id="class_level" name="class_level">
            <option value="Class 10">Class 10 - ₹1</option>
            <option value="Class 11">Class 11 - ₹2</option>
            <option value="Class 12">Class 12 - ₹3</option>
        </select>
        <button type="button" onclick="startPayment()">Pay Now</button>
    </form>

    <script>
        function startPayment() {
            let classLevel = document.getElementById("class_level").value;
            let amount = classLevel === "Class 10" ? 1 :
                         classLevel === "Class 11" ? 2 : 3;

            fetch('CreateOrderServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: `class_level=${classLevel}&amount=${amount}`
            })
            .then(response => response.json())
            .then(order => {
                var options = {
                    "key": "YOUR_KEY_ID",
                    "amount": order.amount,
                    "currency": "INR",
                    "name": "Maths Academy",
                    "description": "Class Video Access",
                    "order_id": order.id,
                    "handler": function (response) {
                        alert("Payment Successful!");
                        window.location.href = "UpdatePaymentServlet?class_level=" + classLevel;
                    },
                    "prefill": { "email": "user@example.com" },
                    "theme": { "color": "#3399cc" }
                };
                var rzp = new Razorpay(options);
                rzp.open();
            });
        }
    </script>
</body>
</html>




