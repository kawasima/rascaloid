SELECT P.*
FROM projects P
JOIN memberships M ON M.project_id = P.project_id
JOIN users U ON U.user_id = M.user_id
WHERE U.account = /*principal.getName()*/'hoge'
