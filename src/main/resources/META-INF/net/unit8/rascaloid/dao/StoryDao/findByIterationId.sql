SELECT S.*
FROM stories S
JOIN iteration_plans IP ON IP.story_id = S.story_id
JOIN iterations I ON IP.iteration_id = I.iteration_id
JOIN projects P ON P.project_id = I.project_id
JOIN memberships M ON M.project_id = P.project_id
JOIN users U ON U.user_id = M.user_id
WHERE I.iteration_id = /*iterationId*/1
  AND U.account = /*principal.getName()*/'hoge'
