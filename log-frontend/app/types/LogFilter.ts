interface LogFilter{
    page?: number;
    size?: number;
    idService: string;
    level?: string;
    refresh?: boolean;
    keySearch?: string|null;
    logTime: number;
}