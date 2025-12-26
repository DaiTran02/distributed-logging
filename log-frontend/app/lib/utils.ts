export function toQueryParams<T extends Record<string, any>>(params: T): string{
    const searchParams = new URLSearchParams();
    Object.entries(params).forEach(([Key,value]) =>{
        if(value === undefined || value === null || value === "") return;

        if(Array.isArray(value)){
            value.forEach(v=>{
                searchParams.append(Key,String(v));
            })
            return;
        }

        searchParams.append(Key,String(value));
    });

    return searchParams.toString();
}