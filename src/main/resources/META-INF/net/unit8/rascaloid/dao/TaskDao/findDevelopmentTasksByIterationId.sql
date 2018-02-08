SELECT DT.*, T.*, TS.name AS status
FROM development_tasks DT
JOIN tasks T ON DT.task_id = T.task_id
JOIN task_status TS ON T.status_id = TS.status_id
JOIN stories S ON S.story_id = DT.story_id
JOIN iteration_plans IP ON IP.story_id = S.story_id
JOIN iterations I ON I.iteration_id = IP.iteration_id
WHERE I.iteration_id = /*iterationId*/1