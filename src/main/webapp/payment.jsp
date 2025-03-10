<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Make Payment</title>
    <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h2>Select Your Class & Pay</h2>
    
    <form id="paymentForm">
        <label>Select Class:</label>
        <select name="class_name" id="class_name">
            <option value="Class 10" data-amount="50000">Class 10 - ₹500</option>
            <option value="Class 11" data-amount="70000">Class 11 - ₹700</option>
            <option value="Class 12" data-amount="90000">Class 12 - ₹900</option>
        </select>

        <button type="button" id="payBtn">Pay Now</button>
    </form>

    <!-- ✅ Hidden Form to Store Class in Session -->
    <form id="sessionForm" action="StoreClassServlet" method="post">
        <input type="hidden" name="selectedClass" id="selectedClass">
        <input type="hidden" name="amount" id="amount">
    </form>

    <script>
document.getElementById('payBtn').onclick = function () {
    var classSelect = document.getElementById('class_name');
    var selectedOption = classSelect.options[classSelect.selectedIndex];
    var amount = selectedOption.getAttribute('data-amount'); // Get amount
    var className = classSelect.value; // Get selected class

    // ✅ Step 1: First, Store Class in Session
    document.getElementById("selectedClass").value = className;
    document.getElementById("amount").value = amount;
    
    var formData = new FormData(document.getElementById("sessionForm"));
    
    fetch("StoreClassServlet", {
        method: "POST",
        body: formData
    })
    .then(() => {
        console.log("✅ Class Stored in Session: " + className);
        
        // ✅ Step 2: After Session is Set, Proceed with Payment
        return fetch('OrderServlet', { 
            method: 'POST', 
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: "amount=" + amount + "&class_name=" + className
        });
    })
    .then(response => response.json())
    .then(data => {
        var options = {
            "key": "rzp_test_cmsgJBXhaTTYHW",
            "amount": amount,
            "currency": "INR",
            "name": "Math Video Platform",
            "description": "Subscription for Class " + className,
            "order_id": data.id,

            "handler": function (response) {
                console.log("✅ Payment Success!", response);
                
                fetch("PaymentSuccessServlet", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: "razorpay_payment_id=" + response.razorpay_payment_id + 
                          "&razorpay_order_id=" + response.razorpay_order_id + 
                          "&razorpay_signature=" + response.razorpay_signature +
                          "&class_name=" + className
                })
                .then(res => res.text())
                .then(data => {
                    console.log("🔹 Server Response:", data);
                    window.location.href = "payment-success.jsp";
                });
            },
            "prefill": { "name": "User Name", "email": "user@example.com", "contact": "9999999999" },
            "theme": { "color": "#3399cc" }
        };
        var rzp1 = new Razorpay(options);
        rzp1.open();
    });
};
</script>

</body>
</html>









