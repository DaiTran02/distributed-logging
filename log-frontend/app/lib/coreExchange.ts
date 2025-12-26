import axios, { AxiosRequestConfig } from "axios";

const baseURL = "http://localhost:9091";

if(!baseURL){
    console.log("Ko tim thay url")
}

const instance = axios.create({
    baseURL,
    timeout: 15000,
    headers:{
        "Content-Type":"application/json"
    }
})

export const CoreExchange = {
  GET<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return instance.get(url, config);
  },

  POST<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return instance.post(url, data, config);
  },

  PUT<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return instance.put(url, data, config);
  },

  DELETE<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return instance.delete(url, config);
  },
};