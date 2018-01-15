SELECT I.*
FROM iterations I
JOIN projects P ON P.project_id = I.project_id
JOIN memberships M ON M.project_id = P.project_id
JOIN users U ON U.user_id = M.user_id
WHERE P.project_id = /*projectId*/1
  AND U.account = /*principal.getName()*/'hoge'
