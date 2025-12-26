export interface ServiceLog {
  id: string;
  logTime: string; // Hoặc Date nếu bạn sẽ convert sau khi fetch
  serviceName: string;
  logLevel: 'INFO' | 'WARN' | 'ERROR' | 'DEBUG'; // Dùng Union Type để chặt chẽ hơn
  threadName: string;
  loggerClass: string;
  message: string;
  logOrigin: string;
  idService: string;
}