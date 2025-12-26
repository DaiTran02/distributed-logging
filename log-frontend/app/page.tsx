import ServiceInfo from "@/components/filters";
import { Input } from "@/components/ui/input";
import LogView from "@/components/LogView";
import { fetchInfoServices } from "@/services/log";

export default async function Home() {
  let listServices;

  try{

  const data = await fetchInfoServices();
  listServices = data.result;
  }catch(err){

  }
  return (<div className="p-5 flex flex-col justify-end">
    <div>
      <LogView  listServices={listServices}/>
    </div>

  </div>);
}
