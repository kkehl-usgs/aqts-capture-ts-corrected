insert
  into time_series_gap_tolerances (time_series_unique_id,
                                   start_time,
                                   end_time,
                                   tolerance_in_minutes,
                                   response_time,
                                   response_version
                                  )
select time_series_unique_id,
       jsonb_extract_path_text(gap_tolerances, 'StartTime')::timestamp start_time,
       jsonb_extract_path_text(gap_tolerances, 'EndTime')::timestamp end_time,
       jsonb_extract_path_text(gap_tolerances, 'ToleranceInMinutes')::numeric tolerance_in_minutes,
       response_time,
       response_version
  from (select jsonb_extract_path_text(json_content, 'UniqueId') time_series_unique_id,
               jsonb_array_elements(jsonb_extract_path(json_content, 'GapTolerances')) gap_tolerances,
               jsonb_extract_path_text(json_content, 'ResponseTime')::timestamp response_time,
               jsonb_extract_path_text(json_content, 'ResponseVersion')::numeric response_version
          from json_data
         where json_data_id = ?) a
