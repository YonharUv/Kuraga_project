function submitForm() {
    var form = document.getElementById('registrationForm');
    if (form.username.value === '' || form.email.value === '' || form.password.value === '' || form.confirmPassword.value === '') {
    alert('Пожалуйста, заполните все обязательные поля.');
    return;
}
    if (!isValidEmail(form.email.value)) {
    alert('Пожалуйста, введите корректный адрес электронной почты.');
    return;
}
    var formData = {
    "username": form.username.value,
    "email": form.email.value,
    "password": form.password.value,
    "confirmPassword": form.confirmPassword.value
};

    var xhr = new XMLHttpRequest();
    xhr.open('POST', form.action, true);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
    xhr.onreadystatechange = function() {
    if (xhr.readyState === 4) {
    var responseContainer = document.getElementById('responseContainer');
    if (xhr.status === 200) {
    form.username.value = '';
    form.email.value = '';
    form.password.value = '';
    form.confirmPassword.value = '';
    alert(xhr.responseText)
    // responseContainer.innerHTML = '<p class="success">' + xhr.responseText + '</p>';
} else {
    response = JSON.parse(xhr.responseText);
    alert(response.message)
    // responseContainer.innerHTML = '<p class="error">' + response.message + '</p>';
}
}
};

    xhr.send(JSON.stringify(formData));
}
function isValidEmail(email) {
        var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}$/;
        return emailRegex.test(email);
    }
function getAllUsers() {
    var button = document.getElementById('getAllUsersButton');
    var usersContainer = document.getElementById('usersDataContainer');

    if (button.textContent === 'Получить всех пользователей') {
    // Показываем кнопку "Свернуть"
    button.textContent = 'Свернуть';

    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/admin/users', true);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
    xhr.onreadystatechange = function() {
    if (xhr.readyState === 4 && xhr.status === 200) {
    var users = JSON.parse(xhr.responseText);

    // Создаем таблицу для отображения данных пользователей
    var tableHtml = '<table border="1">';
    tableHtml += '<tr>';
    for (var key in users[0]) {
    tableHtml += '<th>' + key + '</th>';
}
    tableHtml += '</tr>';

    // Добавляем строки с данными пользователей
    for (var i = 0; i < users.length; i++) {
    tableHtml += '<tr>';
    for (var key in users[i]) {
    tableHtml += '<td>' + users[i][key] + '</td>';
}
    tableHtml += '</tr>';
}

    tableHtml += '</table>';

    // Выводим таблицу на страницу
    usersContainer.innerHTML = tableHtml;

    // Добавляем класс для плавного появления
    usersContainer.classList.add('show');
}
};

    xhr.send();
} else {
    // Скрываем кнопку "Свернуть" и таблицу
    button.textContent = 'Получить всех пользователей';

    // Убираем класс для плавного исчезновения
    usersContainer.classList.remove('show');

    // Очищаем контейнер после завершения анимации
    setTimeout(function() {
    usersContainer.innerHTML = '';
}, 300); // Убедитесь, что это значение соответствует длительности анимации в CSS
}
}
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('getUserByIdButton').addEventListener('click', function () {
        var button = document.getElementById('getUserByIdButton');
        var userContainer = document.getElementById('userByIdContainer');

        if (button.textContent === 'Получить пользователя') {
            // Получаем значение ID пользователя из формы
            userContainer.innerHTML = '';
            var userId = document.getElementById('userId').value;

            // Выполняем AJAX-запрос
            var xhr = new XMLHttpRequest();
            xhr.open('GET', '/admin/users/' + userId, true);

            xhr.onload = function () {
                if (xhr.status === 200) {
                    // Парсим JSON-ответ
                    var userData = JSON.parse(xhr.responseText);

                    // Очищаем контейнер
                    userContainer.innerHTML = '';

                    // Создаем элементы для вывода данных
                    var userDetails = document.createElement('div');
                    userDetails.innerHTML = '<h3>User Details:</h3>';

                    // Добавляем все поля пользователя
                    for (var key in userData) {
                        if (userData.hasOwnProperty(key)) {
                            userDetails.innerHTML += '<p>' + key + ': ' + userData[key] + '</p>';
                        }
                    }

                    // Вставляем элементы в контейнер
                    userContainer.appendChild(userDetails);

                    // Добавляем класс для плавного появления
                    userContainer.classList.add('show');
                    button.textContent = 'Свернуть';
                } else {
                    // Пользователь не найден
                    alert('Пользователя с таким ID нет');
                }
            };

            xhr.send();
        } else {
            // Скрываем кнопку "Свернуть" и содержимое
            button.textContent = 'Получить пользователя';

            // Убираем класс для плавного исчезновения
            userContainer.classList.remove('show');

            // Очищаем контейнер после завершения анимации
             // Убедитесь, что это значение соответствует длительности анимации в CSS
        }
    });

    document.getElementById('findUserByUsernameButton').addEventListener('click', function() {
    var button = document.getElementById('findUserByUsernameButton');
    var userContainer = document.getElementById('userByUsernameResultContainer');

    if (button.textContent === 'Получить пользователя') {
    // Получаем значение имени пользователя из формы
    var username = document.getElementById('searchUsername').value;

    // Выполняем AJAX-запрос
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/admin/users/find/' + username, true);

    xhr.onload = function() {
    if (xhr.status === 200) {
    // Парсим JSON-ответ
    var userData = JSON.parse(xhr.responseText);

    // Очищаем контейнер
    userContainer.innerHTML = '';

    // Создаем элементы для вывода данных
    var userDetails = document.createElement('div');
    userDetails.innerHTML = '<h3>User Details:</h3>';

    // Добавляем все поля пользователя
    for (var key in userData) {
    if (userData.hasOwnProperty(key)) {
    userDetails.innerHTML += '<p>' + key + ': ' + userData[key] + '</p>';
}
}

    // Вставляем элементы в контейнер
    userContainer.appendChild(userDetails);

    // Добавляем класс для плавного появления
    userContainer.classList.add('show');
    button.textContent = 'Свернуть';
} else {
    // Выводим сообщение об ошибке
    alert('Пользователя с таким именем нет')
}
};

    xhr.send();
} else {
    // Скрываем кнопку "Свернуть" и содержимое
    button.textContent = 'Получить пользователя';

    // Убираем класс для плавного исчезновения
    userContainer.classList.remove('show');

    // Очищаем контейнер после завершения анимации
    setTimeout(function() {
    userContainer.innerHTML = '';
}, 300); // Убедитесь, что это значение соответствует длительности анимации в CSS
}
});

    document.getElementById('banUserButton').addEventListener('click', function() {
    // Получаем значение ID пользователя из формы
    var userId = document.getElementById('banUserId').value;

    // Выполняем AJAX-запрос
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/admin/users/' + userId + '/ban', true);

    xhr.onload = function() {
    var banResultContainer = document.getElementById('banResultContainer');

    // Очищаем контейнер
    banResultContainer.innerHTML = '';

    if (xhr.status === 200) {
    // Создаем элемент для вывода результата
    var banResult = document.createElement('p');
    document.getElementById('banUserId').value = '';
    alert("Пользователь успешно забанен")
    banResultContainer.appendChild(banResult);
} else {
    // Выводим сообщение об ошибке
    alert("ID не найден");
}
};

    xhr.send();
});

    document.getElementById('unbanUserButton').addEventListener('click', function() {
        // Получаем значение ID пользователя из формы
        var userId = document.getElementById('unbanUserId').value;

        // Выполняем AJAX-запрос для получения информации о пользователе
        var xhrGetUser = new XMLHttpRequest();
        xhrGetUser.open('GET', '/admin/users/' + userId, true);

        xhrGetUser.onload = function() {
            if (xhrGetUser.status === 200) {
                var userData = JSON.parse(xhrGetUser.responseText);

                // Проверяем роль пользователя
                if (userData.role === 'BANNED') {
                    // Выполняем разбан только для заблокированных пользователей
                    var xhrUnban = new XMLHttpRequest();
                    xhrUnban.open('POST', '/admin/users/' + userId + '/setUser', true);

                    xhrUnban.onload = function() {
                        var unbanResultContainer = document.getElementById('unbanResultContainer');

                        // Очищаем контейнер
                        unbanResultContainer.innerHTML = '';

                        if (xhrUnban.status === 200) {
                            alert("Пользователь успешно разбанен");
                            // Вставляем элемент в контейнер
                            unbanResultContainer.appendChild(unbanResult);
                        } else {
                            // Выводим сообщение об ошибке
                            alert("Ошибка при разбане пользователя");
                        }
                    };

                    xhrUnban.send();
                } else {
                    alert("Невозможно разбанить пользователя. Пользователь не заблокирован.");
                }
            } else {
                alert("ID не найден");
            }
        };

        xhrGetUser.send();
    });


    document.getElementById('setSupervisorButton').addEventListener('click', function() {
    // Получаем значение ID пользователя из формы
    var userId = document.getElementById('supervisorUserId').value;

    // Выполняем AJAX-запрос
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/admin/users/' + userId + '/setSupervisor', true);

    xhr.onload = function() {
    var setSupervisorResultContainer = document.getElementById('setSupervisorResultContainer');

    // Очищаем контейнер
    setSupervisorResultContainer.innerHTML = '';

    if (xhr.status === 200) {
    alert("Привилегии пользователя успешно изменены")

    // Вставляем элемент в контейнер
    setSupervisorResultContainer.appendChild(setSupervisorResult);
} else {
    // Выводим сообщение об ошибке
    alert("ID не найден")
}
};

    xhr.send();
});

    document.getElementById('deleteUserButton').addEventListener('click', function() {
    // Получаем значение ID пользователя из формы
    var userId = document.getElementById('deleteUserId').value;

    // Выполняем AJAX-запрос
    var xhr = new XMLHttpRequest();
    xhr.open('DELETE', '/admin/user/delete/' + userId, true);

    xhr.onload = function() {
    var deleteUserResultContainer = document.getElementById('deleteUserResultContainer');

    // Очищаем контейнер
    deleteUserResultContainer.innerHTML = '';

    if (xhr.status === 200) {
    // Создаем элемент для вывода результата
    var deleteUserResult = document.createElement('p');
    alert("Пользователь удален")

    // Вставляем элемент в контейнер
    deleteUserResultContainer.appendChild(deleteUserResult);
} else {
    // Выводим сообщение об ошибке
    alert("ID не найден")
}
};

    xhr.send();
});

});