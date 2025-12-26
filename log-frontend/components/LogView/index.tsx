"use client";
import { ServiceLog } from "@/app/types/LogType";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "../ui/table";
import { ServiceType } from "@/app/types/ServiceType";
import { Input } from "../ui/input";
import { useEffect, useRef, useState } from "react";
import { useFetchLogByFilter } from "@/hooks/useLogService";
import ServiceFilter from "../filters";
import LogLevelFilter from "../filters/LogLevel";
import SizeFilter from "../filters/Size";
import StateRefresh from "../filters/Refresh";
import DateFilter from "../filters/Date";
interface Props {
  listServices: ServiceType[];
}

const LogView = ({ listServices }: Props) => {
  const [date, setDate] = useState(new Date());
  const [keySearch, setKeySearch] = useState<string>("");
  const [serviceId, setServiceId] = useState("all");
  const [logs, setLogs] = useState<ServiceLog[]>([]);
  const [level, setLevels] = useState("all");
  const [size, setSize] = useState(10);
  const [refresh, setRefresh] = useState(false);
  const [stateRefresh, setStateRefresh] = useState("false");
  let timerRef = useRef<NodeJS.Timeout | null>(null);

  const { data: dataLogs } = useFetchLogByFilter({
    idService: serviceId,
    level: level,
    page: 0,
    size: size,
    refresh: refresh,
    keySearch: keySearch,
    logTime: date.getTime()
  });

  useEffect(() => {
    if (dataLogs) {
      setLogs(dataLogs.result);
      console.log(dataLogs);
    }
  }, [dataLogs]);

  useEffect(() => {
    if (timerRef.current) {
      clearInterval(timerRef.current);
    }
    if (stateRefresh === "true") {
      timerRef.current = setInterval(() => {
        setRefresh((prev) => !prev);
      }, 3000);
    }
    return () => {
      if (timerRef.current) clearInterval(timerRef.current);
    };
  }, [stateRefresh]);

  return (
    <div className="flex flex-col gap-5">
      <div className="flex flex-row w-full gap-3">
        <Input
          type="text"
          placeholder="Tìm..."
          value={keySearch}
          onChange={(e) => setKeySearch(e.target.value)}
        />
        <DateFilter onChange={setDate}/>
        <StateRefresh onChange={setStateRefresh} />
        <SizeFilter onChange={setSize} />
        <LogLevelFilter onChange={setLevels} />
        <ServiceFilter listServices={listServices} onChange={setServiceId} />
      </div>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Time</TableHead>
            <TableHead>Service name</TableHead>
            <TableHead>Log level</TableHead>
            <TableHead>Thread Name</TableHead>
            <TableHead>Logger class</TableHead>
            <TableHead>Message</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {logs &&
            logs.map((log: ServiceLog) => (
              <TableRow key={log.id}>
                <TableCell>{log.logTime}</TableCell>
                <TableCell>{log.serviceName}</TableCell>
                <TableCell>{log.logLevel}</TableCell>
                <TableCell>{log.threadName}</TableCell>
                <TableCell>{log.loggerClass}</TableCell>
                <TableCell className="text-wrap w-200 whitespace-normal">
                  {log.message}
                </TableCell>
              </TableRow>
            ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default LogView;
