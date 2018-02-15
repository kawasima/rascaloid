SELECT DT.*, T.*, TS.name AS status_name
FROM development_tasks DT
JOIN tasks T ON DT.task_id = T.task_id
JOIN task_status TS ON T.status_id = TS.status_id
WHERE T.task_id = /*taskId*/1