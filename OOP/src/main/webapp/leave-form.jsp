<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Leave Form</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        /* Dark theme styles specific to leave-form.jsp */
        body {
            background-color: #222;
            color: #fff;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #333;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        h2 {
            color: #fff;
            margin-bottom: 20px;
        }

        form {
            max-width: 600px;
            margin: 20px auto;
            background: #333;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #ddd;
        }

        input[type="text"], select {
            width: calc(100% - 10px);
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #444;
            border-radius: 4px;
            background-color: #444;
            color: #fff;
        }

        .button-container {
            display: flex;
            justify-content: space-between;
        }

        button {
            background-color: #7747ff;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #5e3ce9;
        }

        .back-button {
            background-color: #555;
        }

        .back-button:hover {
            background-color: #444;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Leave Form</h2>
        
        <c:choose>
            <c:when test="${empty param.action}">
                <!-- Default form for creating a new leave request -->
                <form action="insert" method="post">
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" required>
                    
                    <label for="status">Status:</label>
                    <select id="status" name="status" required>
                        <option value="Pending">Pending</option>
                        <option value="Approved">Approved</option>
                        <option value="Rejected">Rejected</option>
                    </select>
                    
                    <label for="type">Type:</label>
                    <select id="type" name="type" required>
                        <option value="Vacation">Vacation</option>
                        <option value="Sick Leave">Sick Leave</option>
                        <option value="Maternity">Maternity</option>
                    </select>
                    
                    <div class="button-container">
                        <button type="submit">Create</button>
                        <button type="button" class="back-button" onclick="history.back()">Back</button>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <!-- Form for editing an existing leave request -->
                <form action="update" method="post">
                    <input type="hidden" name="id" value="${param.id}">
                    
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" value="${param.name}" required>
                    
                    <label for="status">Status:</label>
                    <select id="status" name="status" required>
                        <option value="Pending" ${param.status eq 'Pending' ? 'selected' : ''}>Pending</option>
                        <option value="Approved" ${param.status eq 'Approved' ? 'selected' : ''}>Approved</option>
                        <option value="Rejected" ${param.status eq 'Rejected' ? 'selected' : ''}>Rejected</option>
                    </select>
                    
                    <label for="type">Type:</label>
                    <select id="type" name="type" required>
                        <option value="Vacation" ${param.type eq 'Vacation' ? 'selected' : ''}>Vacation</option>
                        <option value="Sick Leave" ${param.type eq 'Sick Leave' ? 'selected' : ''}>Sick Leave</option>
                        <option value="Maternity" ${param.type eq 'Maternity' ? 'selected' : ''}>Maternity</option>
                    </select>
                    
                    <div class="button-container">
                        <button type="submit">Update</button>
                        <button type="button" class="back-button" onclick="history.back()">Back</button>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
