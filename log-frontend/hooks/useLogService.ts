import { fetchLogByFilter } from "@/services/log"
import { useQuery } from "@tanstack/react-query"

export const useFetchLogByFilter = (filter: LogFilter) =>{
    return useQuery({
        queryKey: ['logs', filter?.idService, filter?.level, filter.size,filter.refresh,filter?.keySearch,filter.logTime],
        queryFn: () => fetchLogByFilter(filter),
        enabled: true
    })
}