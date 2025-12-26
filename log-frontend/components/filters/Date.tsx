import { useState } from "react"
import { Popover, PopoverContent, PopoverTrigger } from "../ui/popover";
import { Button } from "../ui/button";
import { ChevronDownIcon } from "lucide-react";
import { Calendar } from "../ui/calendar";
import { vi } from "date-fns/locale";

const DateFilter = ({onChange}:{onChange: (item : Date) =>void}) =>{
    const [open, setOpen] = useState(false);
    const [date, setDate] = useState<Date|undefined>(new Date())

    return(
        <Popover open={open} onOpenChange={setOpen}>
            <PopoverTrigger asChild>
                <Button
            variant="outline"
            id="date"
            className="w-48 justify-between font-normal"
          >
            {date ? date.toLocaleDateString() : "Select date"}
            <ChevronDownIcon />
          </Button>
            </PopoverTrigger>
            <PopoverContent className="w-auto overflow-hidden p-0" align="start">
          <Calendar
            mode="single"
            selected={date}
            captionLayout="dropdown"
            locale={vi}
            onSelect={(date) => {
              setDate(date)
              onChange(date === undefined ? new Date() : date);
              setOpen(false)
            }}
          />
        </PopoverContent>
        </Popover>
    );
}

export default DateFilter;