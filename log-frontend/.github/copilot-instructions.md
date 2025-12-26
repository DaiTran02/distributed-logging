# Copilot Instructions for log-frontend

## Project Overview
**log-frontend** is a distributed logging visualization frontend built with Next.js 16 and React 19. It's designed as a UI for monitoring and analyzing logs from distributed systems. The project uses TypeScript with strict mode, Tailwind CSS v4 for styling, and shadcn/ui component patterns.

## Architecture & Key Patterns

### Project Structure
- **`app/`** - Next.js App Router structure (not Pages Router)
  - `layout.tsx` - Root layout with Geist font configuration
  - `page.tsx` - Home page entry point
  - `globals.css` - Tailwind imports and CSS variables (OKLch color system)
- **`lib/`** - Utility functions and shared helpers
  - `utils.ts` - Contains `cn()` utility combining clsx + tailwind-merge for className merging
- **`components.json`** - shadcn/ui configuration (RSC enabled, aliases for `@/components`, `@/ui`, `@/hooks`, `@/lib`)
- **`public/`** - Static assets (SVGs, etc.)

### Component Architecture
- **Server-Side Rendering (RSC)** is enabled by default (`"rsc": true` in components.json)
- Use `"use client"` directive only when needed for interactivity (state, events, hooks)
- Follow shadcn/ui component patterns: components in `components/ui/` directory with variant-based styling using `class-variance-authority`
- Use the `cn()` utility from `lib/utils.ts` to merge Tailwind classes safely

### Styling Conventions
- **Tailwind CSS v4** with postcss integration
- **OKLch color system** for CSS variables (high perceptual uniformity)
- Dark mode support via `.dark` class selector
- Predefined color tokens: `primary`, `secondary`, `accent`, `destructive`, `muted`, plus `chart-*` and `sidebar-*` variants
- Use CSS variables (e.g., `bg-[var(--primary)]`) instead of hardcoding colors
- Animation library: `tw-animate-css` for common animations

### Naming & Conventions
- TypeScript: strict mode enabled, ES2017 target
- Path aliases configured: `@/*` maps to project root (e.g., `import { cn } from "@/lib/utils"`)
- Components should use descriptive, PascalCase names
- Use `tsx` extension for React components (required by config)

## Development Workflow

### Commands
- **`npm run dev`** - Start dev server (hot reload on changes)
- **`npm run build`** - Production build with Next.js optimization
- **`npm run start`** - Run production server
- **`npm run lint`** - Run ESLint (Next.js + TypeScript rules)

### Debugging Tips
- Dev server runs on `http://localhost:3000` by default
- Hot module replacement works automatically; edit `app/page.tsx` to see changes
- Check `.next/` build output for compiled code (not for source inspection)
- Use browser DevTools for CSS debugging; inspect computed styles from Tailwind classes

### Type Safety
- All components should have proper TypeScript types
- Props interfaces should be defined explicitly (e.g., `interface ComponentProps { ... }`)
- React component types: use `React.ReactNode` for children, `React.FC` patterns optional (functional components preferred)

## Integration Points & Dependencies

### External Libraries
- **lucide-react** - Icon library; import as `import { IconName } from "lucide-react"`
- **shadcn/ui** - Component library (use via `components.json` aliases)
- **class-variance-authority** - Type-safe component variants
- **clsx + tailwind-merge** - Already composed into `cn()` utility

### Build Pipeline
- Next.js handles bundling, code splitting, and optimization
- Tailwind CSS compiled at build time via PostCSS
- ESLint auto-fixes on lint runs for common issues

## Critical Files to Know
- [components.json](components.json) - shadcn/ui setup and path aliases
- [app/layout.tsx](app/layout.tsx) - Metadata and font config (update title/description for SEO)
- [app/globals.css](app/globals.css) - All Tailwind imports and CSS variable definitions
- [lib/utils.ts](lib/utils.ts) - `cn()` utility for className handling
- [tsconfig.json](tsconfig.json) - TypeScript config with path aliases
- [next.config.ts](next.config.ts) - Next.js customizations (currently minimal)

## Common Tasks

### Adding a new UI component
1. Create component file in `components/` (or `components/ui/` for reusable primitives)
2. Use `class-variance-authority` for variants if component has style variations
3. Import icons from `lucide-react` as needed
4. Use `cn()` to merge conditional Tailwind classes
5. Export as default or named export

### Adding a new page/route
1. Create folder/file in `app/` following Next.js App Router conventions
2. Export default React component (RSC by default, add `"use client"` if needed)
3. Can optionally export metadata object for SEO

### Styling a component
- Prefer Tailwind utility classes over inline styles
- Use CSS variables from `globals.css` for colors (e.g., `className="bg-[color:var(--primary)]"`)
- Dark mode: conditional classes like `dark:bg-slate-900` or use CSS variable suffixes
- For complex styles, use `cn()` to combine base classes with variants

## Code Quality
- ESLint enforces Next.js and TypeScript best practices
- Strict TypeScript: no implicit any, strict null checks enabled
- Components should be pure functions when possible (avoid side effects in render)
- Use `next/image` for images (not `<img>`) for optimization
