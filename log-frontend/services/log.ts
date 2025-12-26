import { CoreExchange } from "@/app/lib/coreExchange";
import { toQueryParams } from "@/app/lib/utils";

export const fetchLogByFilter = async(filter: LogFilter) => {
    const params = toQueryParams(filter);
    console.log(params);
    const result = await CoreExchange.GET(`/api/v1/logs?${params}`);
    const data = await result.data;
    return data;
}

export const fetchInfoServices = async() =>{
    const result = await CoreExchange.GET(`/api/v1/services`);
    const data = await result.data;
    return data;
}